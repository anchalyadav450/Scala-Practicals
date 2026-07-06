package Practical07

import breeze.linalg._

object MatrixOperations {

  def main(args: Array[String]): Unit = {

    // Create first matrix
    val matrix1 = DenseMatrix(
      (1.0, 2.0),
      (3.0, 4.0)
    )

    // Create second matrix
    val matrix2 = DenseMatrix(
      (5.0, 6.0),
      (7.0, 8.0)
    )

    println("Matrix 1:")
    println(matrix1)

    println("\nMatrix 2:")
    println(matrix2)

    // Element-wise Addition
    val addition = matrix1 + matrix2
    println("\nAddition:")
    println(addition)

    // Element-wise Subtraction
    val subtraction = matrix1 - matrix2
    println("\nSubtraction:")
    println(subtraction)

    // Element-wise Multiplication
    val multiplication = matrix1 *:* matrix2
    println("\nElement-wise Multiplication:")
    println(multiplication)

    // Element-wise Division
    val division = matrix1 /:/ matrix2
    println("\nElement-wise Division:")
    println(division)
  }
}