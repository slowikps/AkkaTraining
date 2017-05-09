package de.zalando.akka.actors

import akka.actor.{Actor, Props}
import de.zalando.akka.AkkaError


object GreeterActor {
  def props: Props = Props(new GreeterActor)
}

class GreeterActor extends Actor {
  override def receive: Receive = {

    /*
        -Implement the below behaviour, for:
              -GreetingRequest(name): return to sender GreetingReply(s"Hello $name")
              -Anything else: return to sender AkkaError("Unknown message received")

        PS: to get ActorRef of the sender use sender() function
        
     */
    case GreetingRequest(name) => sender() ! GreetingReply(s"Hello $name")
    case _ => sender() ! AkkaError("Unknown message received")
  }
}
