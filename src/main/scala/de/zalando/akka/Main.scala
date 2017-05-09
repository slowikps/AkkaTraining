package de.zalando.akka

import akka.actor.ActorSystem
import de.zalando.akka.actors.GreeterActor

case class AkkaError(message: String)

object Main {

  /**
    * Welcome to Akka Into
    * Please use SBT tasks 'next' and 'prev' to navigate between different coding exercises
   */
  def main(args: Array[String]): Unit = {
    val system = ActorSystem("Hello")

    val simpleActor = system.actorOf(GreeterActor.props)

  }
}
