package de.zalando.akka.actors

import akka.actor.{Actor, Props}

case class ResolveTitle(name: String)
case class TitleResolved(title: String)

object TitleResolverActor {
  def props: Props = Props(new TitleResolverActor())
}

class TitleResolverActor extends Actor {
  override def receive: Receive = {
    case ResolveTitle(name) ⇒ sender() ! resolveTitleEnglish(name)
  }

  def resolveTitleEnglish(name: String): TitleResolved =
    TitleResolved(name match {
      case "Zalando" ⇒ "Company"
      case "John" ⇒ "Mr."
      case "Joanna" ⇒ "Mrs."
      case _ ⇒ "Mr."
    })
}
