package de.zalando.akka.actors.circuitbreaker

import java.util.concurrent.atomic.AtomicInteger

import akka.actor.{Actor, Props}
import akka.pattern.ask
import akka.util.Timeout
import de.zalando.akka.AkkaError

import scala.concurrent.duration._

class CircuitBreakerActor(val errorThreshold: Int) extends Actor {

  implicit private val timeout = Timeout(2 seconds)
  implicit private val dispatcher = context.dispatcher

  private val externalService = context.actorOf(ExternalServiceMockActor.props)
  private val counter = new AtomicInteger(0)

  private val _ = context.system.scheduler.schedule(1 seconds, 1 seconds, self, RestartCounter)(context.dispatcher)

  override def receive: Receive = { case in => flowHandler.applyOrElse(in, circuitOpened)}

  private def flowHandler: Receive = {
    case RestartCounter => counter.set(0)
    case _ if counter.get() >= errorThreshold => sender() ! CircuitOpened
  }

  private def circuitOpened: Receive = {
    case message =>
      val senderRef = sender()
      (externalService ? message).map {
        case Success => senderRef ! Success
        case _ =>
          counter.incrementAndGet()
          senderRef ! AkkaError
      }
  }
}

object CircuitBreakerActor {
  def props(errorThreshold: Int): Props = Props(new CircuitBreakerActor(errorThreshold))
}

private case object RestartCounter

case object CircuitOpened
