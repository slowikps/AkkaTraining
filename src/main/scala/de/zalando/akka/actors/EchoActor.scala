package de.zalando.akka.actors

import akka.actor.{Actor, Props}

object EchoActor {
  def props: Props = Props(new EchoActor)
}
class EchoActor extends Actor {
  override def receive: Receive = {
    case message => {
      println(s"Hi I got the following message: $message")
      sender() ! message
    }
  }
}
