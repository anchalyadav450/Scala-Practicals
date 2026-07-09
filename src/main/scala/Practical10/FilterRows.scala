package Practical10

import com.github.tototoshi.csv._
import java.io.File

object FilterRows {

  def main(args: Array[String]): Unit = {

    // Read CSV file
    val reader = CSVReader.open(new File("src/main/scala/Practical10/train.csv"))

    val data = reader.all()
    reader.close()

    // Header and rows
    val header = data.head
    val rows = data.tail

    // Column to filter
    val columnName = "SalePrice"
    val threshold = 200000.0

    val index = header.indexOf(columnName)

    println(s"Rows where $columnName > $threshold")
    println()

    // Print header
    println(header.mkString(","))

    // Filter and print rows
    rows.foreach { row =>
      val value = row(index).trim.toDouble

      if (value > threshold) {
        println(row.mkString(","))
      }
    }

  }
}