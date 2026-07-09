package Practical09

import com.github.tototoshi.csv._
import java.io.File

object MissingValueHandling {

  def main(args: Array[String]): Unit = {

    val reader = CSVReader.open(new File("src/main/scala/Practical09/train.csv"))

    val data = reader.all()
    reader.close()

    val header = data.head
    val rows = data.tail

    // Column to handle missing values
    val columnName = "LotFrontage"

    val index = header.indexOf(columnName)

    val values = rows.flatMap { row =>
      val value = row(index).trim
      if (value == "NA" || value.isEmpty) None
      else Some(value.toDouble)
    }

    val mean = values.sum / values.length

    println(s"Mean of $columnName = $mean")

    println("\nRows after replacing missing values:\n")

    rows.take(10).foreach { row =>
      val updated =
        if (row(index) == "NA" || row(index).trim.isEmpty)
          row.updated(index, f"$mean%.2f")
        else
          row

      println(updated.mkString(","))
    }

  }
}