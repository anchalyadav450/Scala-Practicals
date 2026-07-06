package Practical08

import com.github.tototoshi.csv._
import java.io.File

object CsvStatistics {

  def main(args: Array[String]): Unit = {

    // Read CSV file
    val reader = CSVReader.open(new File("src/main/scala/Practical08/cardekho.csv"))

    val data = reader.all()

    reader.close()

    // Header
    val header = data.head
    val rows = data.tail

    // Numeric columns
    val numericColumns = List(
      "year",
      "selling_price",
      "km_driven",
      "mileage(km/ltr/kg)",
      "engine",
      "max_power",
      "seats"
    )

    for (column <- numericColumns) {

      val index = header.indexOf(column)

      val values = rows.flatMap { row =>
        try {
          Some(row(index).toDouble)
        } catch {
          case _: Exception => None
        }
      }

      if (values.nonEmpty) {
        println(s"\nColumn: $column")
        println(s"Count : ${values.size}")
        println(s"Minimum : ${values.min}")
        println(s"Maximum : ${values.max}")
        println(s"Average : ${values.sum / values.size}")
      }
    }
  }
}
