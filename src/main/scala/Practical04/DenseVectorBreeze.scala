package Practical04

import breeze.linalg.*

object DenseVectorBreeze {

  def main(args: Array[String]): Unit = {

    // Create two dense vectors
    val vector1 = DenseVector(10.0, 20.0, 30.0, 40.0, 50.0)
    val vector2 = DenseVector(5.0, 4.0, 3.0, 2.0, 1.0)

    // Calculate Sum
    val sum = breeze.linalg.sum(vector1)

    // Calculate Mean
    val mean = sum / vector1.length

    // Calculate Dot Product
    val dotProduct = vector1.dot(vector2)

    // Display Results
    println("Vector 1: " + vector1)
    println("Vector 2: " + vector2)
    println("Sum: " + sum)
    println("Mean: " + mean)
    println("Dot Product: " + dotProduct)
  }
}
