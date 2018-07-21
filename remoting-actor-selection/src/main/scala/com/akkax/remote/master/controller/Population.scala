package com.akkax.remote.master.controller

import akka.actor.{ActorSelection, ActorSystem, PoisonPill, Props}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives.{complete, get, path, pathPrefix, _}
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import com.akkax.logger.Logging
import com.akkax.remote.actors.{QueryProcessorActor, Query, Result}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.control.NonFatal

trait Population extends Logging{

  implicit val system: ActorSystem
  implicit val timeout = Timeout(5 seconds)
  val processor: ActorSelection

  val route: Route =
    get {
      pathPrefix("friends") {
        path("country" / IntNumber / "userid" / Segment) { (country, userId) =>
          get {
            complete {
              processReq(country, userId)
            }
          }
        }
      }
    }

  def processReq(countryId: Int, userId: String): Future[HttpResponse] = {
    info(s"Controller: got request found...... $countryId, $userId")
    (processor ? Query(countryId, userId))
      .mapTo[Result]
      .map { res =>
        info(s"Responding with: ${res.toString}")
        HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, res.toString))
      }.recoverWith {
      //Handling actor not reachable
      case NonFatal(_) =>
        val localProcessor = system.actorOf(Props[QueryProcessorActor])
        warn("Remote actor is down or not reachable, so creating local actor for processing message: " + localProcessor.path.name)
        (localProcessor ? Query(countryId, userId))
          .mapTo[Result]
          .map { res =>
            localProcessor ! PoisonPill
            info(s"Responding with: ${res.toString}")
            HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, res.toString))
          }
    }
  }

}
