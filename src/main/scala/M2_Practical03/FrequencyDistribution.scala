package Practical03

import com.github.tototoshi.csv._
import java.io.File

object FrequencyDistribution {

  def main(args: Array[String]): Unit = {

    // Read the CSV file
    val file = new File("src/main/scala/M2_Practical03/brooklyn.csv")
    val reader = CSVReader.open(file)

    val data = reader.all()
    reader.close()

    // Separate header and data
    val header = data.head
    val rows = data.tail

    // Find the retailer_type column
    val columnIndex = header.indexOf("retailer_type")

    // Get actual retailer types from the dataset
    val retailerTypes = rows
      .map(row => row(columnIndex).trim)
      .filter(_.nonEmpty)

    // Calculate frequency
    val frequency = retailerTypes
      .groupBy(identity)
      .map {
        case (retailerType, values) =>
          (retailerType, values.size)
      }
      .toSeq
      .sortBy(_._1)

    // Calculate cumulative frequency
    var cumulativeFrequency = 0

    println("Frequency Distribution and Cumulative Frequency")
    println("-------------------------------------------------------------")
    println(f"${"Retailer Type"}%-30s ${"Frequency"}%-12s ${"Cumulative Frequency"}")
    println("-------------------------------------------------------------")

    frequency.foreach {
      case (retailerType, count) =>

        cumulativeFrequency += count

        println(
          f"$retailerType%-30s $count%-12d $cumulativeFrequency%d"
        )
    }

    println("-------------------------------------------------------------")
    println(s"Total Observations = $cumulativeFrequency")
  }
}