package de.zalando.akka.actors.circuitbreaker

import akka.actor.{Actor, Props}
import de.zalando.akka.AkkaError

class ExternalServiceMockActor extends Actor {

  override def receive: Receive = success

  private def success: Receive = {
    case _ =>
      context.become(failure)
      sender() ! Success
  }

  private def failure: Receive = {
    case _ =>
      context.become(success)
      sender() ! AkkaError
  }
}

object ExternalServiceMockActor {
  def props: Props = Props(new ExternalServiceMockActor)
}

object Success