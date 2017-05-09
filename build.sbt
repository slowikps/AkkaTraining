name := "AkkaTraining"

version := "1.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.17"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-core" % "10.0.3",

  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

lazy val next = taskKey[Unit]("next exercise")
lazy val current = taskKey[Unit]("next exercise")
lazy val prev = taskKey[Unit]("previous exercise")

next := {
  val base = (baseDirectory in ThisBuild).value
  val totalEx: Int = ExHelper.totalEx(base)
  val currentExNum : Int = ExHelper.currentExNum(base)

  if(totalEx == currentExNum){
    println("Congrats, You have already finished your code exercises, It looks like it was super easy for you!")
  }else{
    ExHelper.copyExercise((baseDirectory in ThisBuild).value, currentExNum+1)
    println(s"Exercise no. ${currentExNum+1} has been added, Enjoy!")
  }
}

current := {
  val base = (baseDirectory in ThisBuild).value
  val currentExNum : Int = ExHelper.currentExNum(base)

  if(currentExNum == 0)
    println(s"Now you have a fresh and green playground. please use the command 'next' to start the fun :)")
  else if(currentExNum >= 1)
    println(s"You are now on Exercise no. $currentExNum, Go Go Go!")

}

prev := {
  val base = (baseDirectory in ThisBuild).value
  val currentExNum : Int = ExHelper.currentExNum(base)

  if(currentExNum == 0) {
    println(s"There no place to go backwards :)  LET'S GO FORWARD")
  }else if(currentExNum == 1){
    ExHelper.deleteTest((baseDirectory in ThisBuild).value, currentExNum)
    println(s"Now you have a fresh and green playground. please use the command 'next' to start the fun :)")
  }else if(currentExNum > 1){
    ExHelper.deleteTest((baseDirectory in ThisBuild).value, currentExNum)
    println(s"Switched back to Exercise no. ${currentExNum-1}, You are good to go!")
  }
}


    