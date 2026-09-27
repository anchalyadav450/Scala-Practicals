package M2_Practical02

import scala.io.Source
import breeze.linalg._
import breeze.plot._

object MovingAveragePractical {

  // Simple Moving Average
  def sma(data: List[Double], window: Int): List[Double] = {
    data.sliding(window)
      .map(values => values.sum / window)
      .toList
  }

  // Weighted Moving Average
  def wma(data: List[Double], window: Int): List[Double] = {
    data.sliding(window)
      .map { values =>

        val weights = (1 to window).map(_.toDouble)

        values
          .zip(weights)
          .map {
            case (value, weight) =>
              value * weight
          }
          .sum / weights.sum
      }
      .toList
  }

  // Exponential Moving Average
  def ema(data: List[Double], alpha: Double): List[Double] = {

    var result = List(data.head)

    for (i <- 1 until data.length) {

      val next =
        alpha * data(i) +
          (1 - alpha) * result.last

      result = result :+ next
    }

    result
  }

  def main(args: Array[String]): Unit = {

    // Load dataset from resources folder
    val stream =
      getClass.getResourceAsStream("/daily-min-temperatures.csv")

    if (stream == null) {

      println("Dataset not found!")
      println("Please place daily-min-temperatures.csv inside:")
      println("src/main/resources/")

      return
    }

    val source = Source.fromInputStream(stream)

    val temp =
      source
        .getLines()
        .drop(1)
        .flatMap { line =>

          val cols = line.split(",")

          if (cols.length > 1) {
            cols(1).trim.toDoubleOption
          } else {
            None
          }
        }
        .take(100)
        .toList

    source.close()

    if (temp.isEmpty) {

      println("No temperature data found!")
      return
    }

    // Moving average window
    val window = 5

    // Calculate SMA
    val smaValues =
      sma(temp, window)

    // Calculate WMA
    val wmaValues =
      wma(temp, window)

    // Calculate EMA
    val emaValues =
      ema(temp, 0.3)

    // Display information
    println("==============================================")
    println("       MOVING AVERAGE ANALYSIS")
    println("       Daily Minimum Temperature Dataset")
    println("==============================================")

    println()
    println("Dataset Loaded Successfully")
    println("Records = " + temp.length)

    println()
    println("Moving Average Window = " + window)

    println()
    println("First 10 SMA Values")
    println("----------------------------------------------")

    smaValues.take(10).foreach(println)

    println()
    println("First 10 WMA Values")
    println("----------------------------------------------")

    wmaValues.take(10).foreach(println)

    println()
    println("First 10 EMA Values")
    println("----------------------------------------------")

    emaValues.take(10).foreach(println)

    // ---------------------------------------------
    // Create graph
    // ---------------------------------------------

    val fig = Figure()
    val p = fig.subplot(0)

    // Original temperature data
    val x1 =
      DenseVector(
        (1 to temp.length)
          .map(_.toDouble)
          .toArray
      )

    val y1 =
      DenseVector(temp.toArray)

    // SMA and WMA start from the 5th day
    val x2 =
      DenseVector(
        (window to temp.length)
          .map(_.toDouble)
          .toArray
      )

    val y2 =
      DenseVector(smaValues.toArray)

    val y3 =
      DenseVector(wmaValues.toArray)

    // EMA has the same length as original data
    val y4 =
      DenseVector(emaValues.toArray)

    // Plot original temperature
    p += plot(x1, y1)

    // Plot SMA
    p += plot(x2, y2)

    // Plot WMA
    p += plot(x2, y3)

    // Plot EMA
    p += plot(x1, y4)

    // Graph labels
    p.xlabel = "Days"
    p.ylabel = "Temperature"
    p.title = "Daily Temperature with SMA, WMA and EMA"

    fig.refresh()

    println()
    println("Graph generated successfully.")
    println("==============================================")
  }
}