package M2_Practical08

import breeze.linalg.DenseVector
import com.github.tototoshi.csv._
import java.io.File

object KMeansClustering {

  // Calculate Euclidean distance between two Breeze vectors
  def euclideanDistance(
                         v1: DenseVector[Double],
                         v2: DenseVector[Double]
                       ): Double = {

    math.sqrt(
      (v1 - v2).data.map(x => x * x).sum
    )
  }

  def main(args: Array[String]): Unit = {

    // --------------------------------------------------
    // Read BankNote Authentication CSV file
    // --------------------------------------------------

    val file = new File(
      "src/main/scala/M2_Practical08/BankNoteAuthentication.csv"
    )

    val reader = CSVReader.open(file)
    val data = reader.all()
    reader.close()

    val header = data.head
    val rows = data.tail

    println("==============================================")
    println("             K-MEANS CLUSTERING")
    println("       BankNote Authentication Dataset")
    println("==============================================")

    println(s"Total Records : ${rows.length}")

    // --------------------------------------------------
    // Find feature columns
    // --------------------------------------------------

    val varianceIndex = header.indexOf("variance")
    val skewnessIndex = header.indexOf("skewness")
    val curtosisIndex = header.indexOf("curtosis")
    val entropyIndex = header.indexOf("entropy")

    if (
      varianceIndex == -1 ||
        skewnessIndex == -1 ||
        curtosisIndex == -1 ||
        entropyIndex == -1
    ) {

      println()
      println("ERROR: Required columns were not found.")
      println()
      println("Available columns:")
      println(header.mkString(", "))

      return
    }

    // --------------------------------------------------
    // Convert dataset into Breeze vectors
    // --------------------------------------------------

    val dataVectors = rows.map { row =>

      DenseVector(
        row(varianceIndex).trim.toDouble,
        row(skewnessIndex).trim.toDouble,
        row(curtosisIndex).trim.toDouble,
        row(entropyIndex).trim.toDouble
      )
    }.toArray

    // --------------------------------------------------
    // K-Means parameters
    // --------------------------------------------------

    val k = 2
    val maxIterations = 100

    println(s"Number of Clusters (K) : $k")
    println("Features Used          : variance, skewness, curtosis, entropy")

    // --------------------------------------------------
    // Initialize two centroids
    // --------------------------------------------------

    var centroids = Array(
      dataVectors(0).copy,
      dataVectors(dataVectors.length / 2).copy
    )

    // Cluster assignment for every record
    var assignments =
      Array.fill(dataVectors.length)(-1)

    var previousAssignments =
      Array.fill(dataVectors.length)(-2)

    var iteration = 0
    var converged = false

    // --------------------------------------------------
    // K-Means Algorithm
    // --------------------------------------------------

    while (iteration < maxIterations && !converged) {

      println()
      println(s"--- Iteration ${iteration + 1} ---")

      // ------------------------------------------------
      // Step 1: Assignment Step
      // Assign each record to nearest centroid
      // ------------------------------------------------

      for (i <- dataVectors.indices) {

        val point = dataVectors(i)

        var minimumDistance = Double.MaxValue
        var nearestCluster = -1

        for (j <- 0 until k) {

          val distance =
            euclideanDistance(point, centroids(j))

          if (distance < minimumDistance) {

            minimumDistance = distance
            nearestCluster = j
          }
        }

        assignments(i) = nearestCluster
      }

      // ------------------------------------------------
      // Check convergence
      // ------------------------------------------------

      if (assignments.sameElements(previousAssignments)) {

        converged = true

      } else {

        previousAssignments = assignments.clone()
      }

      // ------------------------------------------------
      // Step 2: Update Step
      // Calculate new centroid of each cluster
      // ------------------------------------------------

      val newCentroids = Array(
        DenseVector.zeros[Double](4),
        DenseVector.zeros[Double](4)
      )

      val clusterCounts =
        Array.fill(k)(0)

      for (i <- dataVectors.indices) {

        val clusterId = assignments(i)

        newCentroids(clusterId) =
          newCentroids(clusterId) + dataVectors(i)

        clusterCounts(clusterId) += 1
      }

      // Calculate mean for each cluster
      for (j <- 0 until k) {

        if (clusterCounts(j) > 0) {

          newCentroids(j) =
            newCentroids(j) / clusterCounts(j).toDouble
        }
      }

      centroids = newCentroids

      println(
        s"Cluster 0 : ${clusterCounts(0)} records"
      )

      println(
        s"Cluster 1 : ${clusterCounts(1)} records"
      )

      iteration += 1
    }

    // --------------------------------------------------
    // Final Results
    // --------------------------------------------------

    println()
    println("==============================================")
    println("              FINAL RESULTS")
    println("==============================================")

    println(
      s"K-Means converged in $iteration iterations."
    )

    println()
    println("Final Centroids")
    println("----------------------------------------------")

    println(
      s"Cluster 0 : ${centroids(0)}"
    )

    println(
      s"Cluster 1 : ${centroids(1)}"
    )

    // --------------------------------------------------
    // Final cluster sizes
    // --------------------------------------------------

    val finalCounts =
      Array.fill(k)(0)

    for (cluster <- assignments) {

      finalCounts(cluster) += 1
    }

    println()
    println("Final Cluster Sizes")
    println("----------------------------------------------")

    println(
      s"Cluster 0 : ${finalCounts(0)} records"
    )

    println(
      s"Cluster 1 : ${finalCounts(1)} records"
    )

    // --------------------------------------------------
    // Display first 10 cluster assignments
    // --------------------------------------------------

    println()
    println("Sample Cluster Assignments")
    println("----------------------------------------------")

    val sampleSize =
      math.min(10, dataVectors.length)

    for (i <- 0 until sampleSize) {

      println(
        s"Record ${i + 1} -> Cluster ${assignments(i)}"
      )
    }

    println("----------------------------------------------")
    println("Program completed successfully.")
    println("==============================================")
  }
}