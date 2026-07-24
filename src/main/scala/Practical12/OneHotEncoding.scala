package Practical12

import scala.io.Source

object OneHotEncoding {

  def main(args: Array[String]): Unit = {

    val fileName = "src/main/scala/Practical12/airline_passenger_satisfaction.csv"

    // Read CSV file
    val source = Source.fromFile(fileName)
    val lines = source.getLines().toList
    source.close()

    // Header
    val header = lines.head.split(",", -1)

    // Find Gender column index
    val genderIndex = header.indexOf("Gender")

    println("One-Hot Encoding for Gender")
    println("----------------------------")

    // Display original and encoded values
    lines.tail.take(10).foreach { line =>

      val columns = line.split(",", -1)
      val gender = columns(genderIndex)

      val male = if (gender == "Male") 1 else 0
      val female = if (gender == "Female") 1 else 0

      println(
        "Gender: " + gender +
          " | Male: " + male +
          " | Female: " + female
      )
    }
  }
}