package Practical05

import breeze.linalg.*

object MatrixTranspose {

  def main(args: Array[String]): Unit = {


    val matrix = DenseMatrix(
      (1.0, 2.0, 3.0),
      (4.0, 5.0, 6.0),
      (7.0, 8.0, 10.0)
    )


    val transposeMatrix = matrix.t


    val determinant = det(matrix)


    println("Original Matrix:")
    println(matrix)

    println("\nTranspose Matrix:")
    println(transposeMatrix)

    println("\nDeterminant:")
    println(determinant)
  }
}
