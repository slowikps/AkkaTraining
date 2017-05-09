package de.zalando.akka.actors

case class GreetingRequest(name: String)
case class GreetingReply(message: String)

case class ChangeGreetingRequest(greetingType: String)

case class ChangeGreetingResponse()