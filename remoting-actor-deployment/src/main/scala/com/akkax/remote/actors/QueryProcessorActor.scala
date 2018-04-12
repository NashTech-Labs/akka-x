package com.akkax.remote.actors

import akka.actor.Actor
import com.akkax.logger.Logging
import scala.util.Random


case class Query(country: Int, query: String)

case class Result(country: String, population: Long, message: String)

/**
  * Created by girish on 19/12/17.
  */
class QueryProcessorActor extends Actor with CountryMap with Logging {

  info("*******Creating file processor actor.................")

  override def receive: Receive = {
    case query: Query =>
      info(s"QueryProcessorActor: actor: ${self.path} processing query: $query")
      Thread.sleep(10) //Processing time
      sender ! Result(countries.getOrElse(query.country, "None"), Random.nextInt(), "Success")
  }

}

trait CountryMap {

  val countries = Map(
    1 -> "India",
    2 -> "US",
    3 -> "Australia",
    4 -> "Brazil",
    5 -> "Shree Lanka"
  )

}
