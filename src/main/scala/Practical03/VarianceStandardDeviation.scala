package Practical03

import scala.util.Random

object VarianceStandardDeviation {

  def main(args: Array[String]): Unit = {

    // Generate 10 random numbers between 1 and 100
    val numbers = List.fill(10)(Random.nextInt(100) + 1)

    println("Random Dataset: " + numbers)

    // Calculate Mean
    val mean = numbers.sum.toDouble / numbers.length

    // Calculate Variance
    val variance = numbers.map(x => math.pow(x - mean, 2)).sum / numbers.length

    // Calculate Standard Deviation
    val standardDeviation = math.sqrt(variance)

    println("Mean: " + mean)
    println("Variance: " + variance)
    println("Standard Deviation: " + standardDeviation)

  }
}
