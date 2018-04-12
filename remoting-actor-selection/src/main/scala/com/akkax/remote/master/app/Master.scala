package com.akkax.remote.master.app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.akkax.logger.Logging
import com.akkax.remote.master.controller.Population
import com.typesafe.config.ConfigFactory

/**
  * Created by girish on 15/1/18.
  */
object Master extends Population with Logging {

  val config = ConfigFactory.load
  val app = config.getString("application.name")

  info("Application conf......" + app)

  override implicit val system = ActorSystem(app)

  //Akka remoting actor selection
  val processor = system.actorSelection(s"akka.tcp://$app@127.0.0.1:2552/user/processor")

  def main(args: Array[String]) {
    implicit val materializer = ActorMaterializer()

    Http().bindAndHandle(route, "localhost", 8081)
    println(s"Server online at http://localhost:8081")
  }
}
