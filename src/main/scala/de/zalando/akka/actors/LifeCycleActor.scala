package de.zalando.akka.actors

import akka.actor.{Actor, ActorRef, Props}

object LifeCycleActor {
  def props(manager: ActorRef): Props = Props(new LifeCycleActor(manager))
}

class LifeCycleActor(manager: ActorRef) extends Actor {

  val preStartMsg = "I'll Start ..."
  def preRestartMsg(reason: Throwable) = s"I'll Restart because: ${reason.getMessage}"
  def postRestartMsg(reason: Throwable) = s"I've Restarted because: ${reason.getMessage}"
  val postStopMsg = "I've been stopped, bye ..."

  override def receive: Receive = {
    case "check" ⇒ sender() ! "checked"
    case e: Exception ⇒ throw e
    case "stop" ⇒ context stop self
  }


  override def preStart() = manager ! preStartMsg

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("I am in preRestart")
    manager ! preRestartMsg(reason)
//    super.preRestart(reason, message)
  }

  override def postStop(): Unit = manager ! postStopMsg

  override def postRestart(reason: Throwable): Unit = {
    println("I am in postRestart")
    manager ! postRestartMsg(reason)
//    super.postRestart(reason)
  }

  // use actor life cycle call-backs to implement expected behaviour in E6p1Test
}
