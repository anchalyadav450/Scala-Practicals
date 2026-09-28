package M2_Practical14

object TimeSeriesAnalysis {

  def main(args: Array[String]): Unit = {

    // Generate synthetic daily sales data for one month
    val salesData = (1 to 30).map { day =>
      val sales = 100 + (day * 5) + ((day % 5) * 10)
      (day, sales)
    }

    println("==============================================")
    println("       BASIC TIME SERIES ANALYSIS")
    println("       Daily Sales for One Month")
    println("==============================================")

    println()
    println("Day\tSales")
    println("----------------")

    salesData.foreach {
      case (day, sales) =>
        println(s"$day\t$sales")
    }

    // Extract sales values
    val salesValues = salesData.map(_._2)

    // Basic time series analysis
    val totalSales = salesValues.sum
    val averageSales = salesValues.sum.toDouble / salesValues.size
    val minimumSales = salesValues.min
    val maximumSales = salesValues.max

    val minimumDay = salesData.minBy(_._2)._1
    val maximumDay = salesData.maxBy(_._2)._1

    println()
    println("==============================================")
    println("             TIME SERIES ANALYSIS")
    println("==============================================")

    println(s"Total Sales       : $totalSales")
    println(f"Average Sales     : $averageSales%.2f")
    println(s"Minimum Sales     : $minimumSales")
    println(s"Minimum Sales Day : Day $minimumDay")
    println(s"Maximum Sales     : $maximumSales")
    println(s"Maximum Sales Day : Day $maximumDay")

    println()
    println("Inference:")
    println("The synthetic time series shows daily sales")
    println("for a period of 30 days.")
    println("The average, minimum and maximum sales help")
    println("to understand the basic pattern of the time series.")

    if (salesData.last._2 > salesData.head._2) {
      println("Overall Trend     : Increasing")
    } else if (salesData.last._2 < salesData.head._2) {
      println("Overall Trend     : Decreasing")
    } else {
      println("Overall Trend     : Stable")
    }

    println("==============================================")
    println("Program completed successfully.")
    println("==============================================")
  }
}