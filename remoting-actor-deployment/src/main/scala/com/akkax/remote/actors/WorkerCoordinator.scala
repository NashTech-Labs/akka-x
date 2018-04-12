package com.akkax.remote.actors

import akka.actor.{Actor, Props}
import com.akkax.logger.Logging

/**
  * Created by girish on 23/12/17.
  */
class WorkerCoordinator extends Actor with Logging{

  override def preStart(): Unit = {
    super.preStart()
    info("*******WorkerCoordinator: Starting the actor..........")
  }

  def receive = {
    case msg: Query =>
      info(s"DeciderGuardian: ${self.path}, got message: ${msg}")
      val name = s"Country${msg.country}"
      val actor = context.child(name) getOrElse context.actorOf(Props[QueryProcessorActor], name)
      actor forward msg
  }
}
