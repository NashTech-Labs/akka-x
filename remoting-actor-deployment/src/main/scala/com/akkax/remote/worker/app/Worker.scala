package com.akkax.remote.worker.app

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool
import com.akkax.logger.Logging
import com.akkax.remote.actors.WorkerCoordinator
import com.typesafe.config.ConfigFactory


object Worker extends App with Logging{

  val config = ConfigFactory.load

  val app = config.getString("application.name")
  implicit val system = ActorSystem(app, config)

//  val processor: ActorRef = system
//    .actorOf(RoundRobinPool(20).props(Props(classOf[WorkerCoordinator])), "processor")

  info("Worker is ready to process work..")
}
