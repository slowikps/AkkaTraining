package de.zalando.akka.actors

import akka.actor.{Actor, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._


object PoliteGreeterActor {
  def props: Props = Props(new PoliteGreeterActor())
}

class PoliteGreeterActor extends Actor {

  implicit val timeout = Timeout(2 seconds)
  implicit val dispatcher = context.dispatcher

  val titleResolved = context.actorOf(TitleResolverActor.props)

  /*
      We are polite people, so let's greet people with their titles. there is a smart AI actor called TitleResolverActor
      TitleResolverActor is not very smart yet (it only knows the titles of John, Joanna and Zalando)
      You send it a ResolveTitle({name}) and it replies back with TitleResolved({title}) let's use it to reply for each:
        GreetingRequest({name}) with GreetingReply("Hello {title} {name}")

      PS: you will need ask pattern
          To enable ask pattern you will need 'import akka.pattern.ask' and do some other stuff :)


      For the second requirement: We want to change the actor behavior from GreetingReply("Hello {title} {name}") to
      be like GreetingReply("Good morning {name}")

      PS: you will need to use become
   */
  //foo.applyOrElse(_: Int, (_: Int).toString)
  override def receive = { case in: Any => contextChange.applyOrElse(in, titleGreet)}

  def contextChange: Receive = {
    case ChangeGreetingRequest("TIME") => {
      context.become({ case in: Any => contextChange.applyOrElse(in, goodMorningGreet)})
      sender() ! ChangeGreetingResponse()
    }
    case ChangeGreetingRequest("TIME2") => {
      context.become({ case in: Any => contextChange.applyOrElse(in, titleGreet)})
      sender() ! ChangeGreetingResponse()
    }
  }

  private def titleGreet: Receive = {
    case GreetingRequest(name) => {
      val ref = sender()

      (titleResolved ? ResolveTitle(name))
        .onSuccess {
          case TitleResolved(title) => ref ! GreetingReply(s"Hello $title $name")
        }
    }
  }

  def goodMorningGreet: Receive = {
    case GreetingRequest(name) => sender() ! GreetingReply(s"Good morning $name")
  }
}
