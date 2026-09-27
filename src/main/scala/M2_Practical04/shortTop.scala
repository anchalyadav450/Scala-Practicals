package Practical04

import com.github.tototoshi.csv._
import java.io.File

object SortTop5 {

  def main(args: Array[String]): Unit = {

    // Read the CSV file
    val file = new File("src/main/scala/M2_Practical04/brooklyn.csv")
    val reader = CSVReader.open(file)

    val data = reader.all()
    reader.close()

    // Separate header and data
    val header = data.head
    val rows = data.tail

    // Find the retailer_type column
    val columnIndex = header.indexOf("retailer_type")

    // Sort the dataset by retailer_type
    val sortedRows = rows
      .filter(row => row(columnIndex).trim.nonEmpty)
      .sortBy(row => row(columnIndex).trim)

    // Extract top 5 rows
    val topFiveRows = sortedRows.take(5)

    println("Top 5 Rows After Sorting by Retailer Type")
    println("--------------------------------------------------")
    println(header.mkString(" | "))
    println("--------------------------------------------------")

    topFiveRows.foreach { row =>
      println(row.mkString(" | "))
    }

    println("--------------------------------------------------")
  }
}