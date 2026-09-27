package M2_Practical07

import breeze.linalg.DenseVector
import com.github.tototoshi.csv._
import java.io.File

object EuclideanNearestNeighbor {

  // Function to calculate Euclidean distance
  def euclideanDistance(
                         v1: DenseVector[Double],
                         v2: DenseVector[Double]
                       ): Double = {

    math.sqrt(
      (v1 - v2).data.map(x => x * x).sum
    )
  }

  def main(args: Array[String]): Unit = {

    // CSV file
    val file = new File(
      "src/main/scala/M2_Practical07/BankNoteAuthentication.csv"
    )

    val reader = CSVReader.open(file)
    val data = reader.all()
    reader.close()

    val header = data.head
    val rows = data.tail

    println("==============================================")
    println("       EUCLIDEAN DISTANCE")
    println("       NEAREST NEIGHBOR CLASSIFICATION")
    println("       BankNote Authentication Dataset")
    println("==============================================")

    println(s"Total Records : ${rows.length}")

    // Find column indexes
    val varianceIndex = header.indexOf("variance")
    val skewnessIndex = header.indexOf("skewness")
    val curtosisIndex = header.indexOf("curtosis")
    val entropyIndex = header.indexOf("entropy")
    val classIndex = header.indexOf("class")

    // Check columns
    if (
      varianceIndex == -1 ||
        skewnessIndex == -1 ||
        curtosisIndex == -1 ||
        entropyIndex == -1 ||
        classIndex == -1
    ) {

      println()
      println("ERROR: Required columns were not found.")
      println()
      println("Available columns:")
      println(header.mkString(", "))

      return
    }

    // Convert CSV data into Breeze vectors
    val dataset = rows.map { row =>

      val features = DenseVector(
        row(varianceIndex).trim.toDouble,
        row(skewnessIndex).trim.toDouble,
        row(curtosisIndex).trim.toDouble,
        row(entropyIndex).trim.toDouble
      )

      val label = row(classIndex).trim.toInt

      (features, label)
    }

    // First record is used as the test point
    val testPoint = dataset.head._1
    val actualClass = dataset.head._2

    println()
    println("Test Record")
    println("----------------------------------------------")
    println(s"Features     : $testPoint")
    println(s"Actual Class : $actualClass")

    // Calculate distance from test point
    // to every other record
    val distances = dataset.tail.map {

      case (features, label) =>

        val distance =
          euclideanDistance(testPoint, features)

        (distance, label)
    }

    // Find the nearest neighbor
    val nearest = distances.minBy(_._1)

    val nearestDistance = nearest._1
    val predictedClass = nearest._2

    println()
    println("Nearest Neighbor")
    println("----------------------------------------------")
    println(f"Euclidean Distance : $nearestDistance%.4f")
    println(s"Nearest Class      : $predictedClass")

    // Classification result
    println()
    println("Classification Result")
    println("----------------------------------------------")

    if (predictedClass == actualClass) {
      println("Prediction : Correct")
    } else {
      println("Prediction : Incorrect")
    }

    println("----------------------------------------------")
    println("Program completed successfully.")
    println("==============================================")
  }
}