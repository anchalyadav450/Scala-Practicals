package Practical06

import breeze.linalg._

object MatrixSlice {
  def main(args: Array[String]): Unit = {

    // Create a 4x4 matrix
    val matrix = DenseMatrix(
      (1, 2, 3, 4),
      (5, 6, 7, 8),
      (9, 10, 11, 12),
      (13, 14, 15, 16)
    )

    println("Original Matrix:")
    println(matrix)

    // Extract sub-matrix (rows 1 to 2, columns 1 to 2)
    val subMatrix = matrix(1 to 2, 1 to 2)

    println("\nSub-Matrix:")
    println(subMatrix)

    // Row sums
    val rowSums = sum(subMatrix(*, ::))

    println("\nRow Sums:")
    println(rowSums)

    // Column sums
    val columnSums = sum(subMatrix(::, *))

    println("\nColumn Sums:")
    println(columnSums)
  }
}