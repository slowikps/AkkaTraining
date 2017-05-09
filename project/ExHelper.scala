import sbt._

object ExHelper {

  val exercisesFolder = "exercises"
  val testFolder = "src/test"
  val packageName = "scala/de/zalando/akka/actors"

  def totalEx(base: File): Int = ((base / exercisesFolder) ** "*.exa").get.length
  def currentExNum(base: File): Int = ((base / testFolder) ** "*Test.scala").get.length

  private def getExercises(base: File, i: Int): Seq[File] = ((base / exercisesFolder) ** s"E$i*.exa").get
  private def getTests(base: File, i: Int): Seq[File] = ((base / testFolder) ** s"E$i*Test.scala").get

  def copyExercise(base: File, i: Int) = {
    val exs = getExercises(base, i)
    exs.foreach(file ⇒ IO.copyFile(file, new File(file.getPath.replace(exercisesFolder, s"$testFolder/$packageName")
      .replace(".exa", "Test.scala"))))
  }

  def deleteTest(base: File, i: Int) = {
    val exs = getTests(base, i)
    exs.foreach(IO.delete)
  }
}