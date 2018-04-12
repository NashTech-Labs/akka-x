package com.akkax.remote.master.app

import akka.actor.{ActorRef, ActorSystem, Address, Deploy, Props}
import akka.http.scaladsl.Http
import akka.remote.RemoteScope
import akka.routing.FromConfig
import akka.stream.ActorMaterializer
import com.akkax.logger.Logging
import com.akkax.remote.actors.QueryProcessorActor
import com.akkax.remote.master.controller.Population
import com.typesafe.config.ConfigFactory

/**
  * Created by girish on 15/1/18.
  */
object Master extends Population with Logging{

  val config = ConfigFactory.load
  val app = config.getString("application.name")

  info("Application conf......" + app)

  override implicit val system = ActorSystem(app)

  //Akka remoting actor selection with single actor
  //val processor: ActorRef = system.actorOf(Props[QueryProcessorActor], "sampleActor")



  //Akka remoting actor selection with router from config
  lazy val processor: ActorRef = system.actorOf(FromConfig.props(Props[QueryProcessorActor]), "processors")

  //Actor deployment with code
  val address = Address("akka.tcp", "population", "localhost", 2553)
//  val ref = system.actorOf(Props[QueryProcessorActor].withDeploy(Deploy(scope = RemoteScope(address))))

  def main(args: Array[String]) {
    implicit val materializer = ActorMaterializer()

    Http().bindAndHandle(route, "localhost", 8081)
    println(s"Server online at http://localhost:8081")
  }
}
