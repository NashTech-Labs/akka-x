package com.akkax.remote.worker.app

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool
import com.akkax.logger.Logging
import com.akkax.remote.actors.WorkerCoordinator
import com.typesafe.config.ConfigFactory

/**
  * Starts actor system without starting any actor
  */
object Worker extends App with Logging{

  val config = ConfigFactory.load

  val app = config.getString("application.name")
  implicit val system = ActorSystem(app, config)

  info("Worker is ready to process work..")
}
