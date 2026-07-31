package M2_Practical01

import scala.io.Source
import breeze.linalg._
import breeze.plot._

object MovingAveragePractical {

  def sma(data: List[Double], window: Int): List[Double] =
    data.sliding(window).map(_.sum / window).toList

  def wma(data: List[Double], window: Int): List[Double] =
    data.sliding(window).map { values =>
      val weights = (1 to window).map(_.toDouble)
      values.zip(weights).map { case (v, w) => v * w }.sum / weights.sum
    }.toList

  def ema(data: List[Double], alpha: Double): List[Double] = {
    var result = List(data.head)
    for (i <- 1 until data.length) {
      val next = alpha * data(i) + (1 - alpha) * result.last
      result = result :+ next
    }
    result
  }

  def main(args: Array[String]): Unit = {

    val stream = getClass.getResourceAsStream("/daily-min-temperatures.csv")

    if (stream == null) {
      println("Dataset not found!")
      return
    }

    val source = Source.fromInputStream(stream)

    val temp = source.getLines()
      .drop(1)
      .flatMap { line =>
        val cols = line.split(",")
        if (cols.length > 1) cols(1).trim.toDoubleOption else None
      }
      .take(100)
      .toList

    source.close()

    val window = 5

    val smaValues = sma(temp, window)
    val wmaValues = wma(temp, window)
    val emaValues = ema(temp, 0.3)

    println("Dataset Loaded Successfully")
    println("Records = " + temp.length)

    println("\nFirst 10 SMA")
    smaValues.take(10).foreach(println)

    println("\nFirst 10 WMA")
    wmaValues.take(10).foreach(println)

    println("\nFirst 10 EMA")
    emaValues.take(10).foreach(println)

    val fig = Figure()
    val p = fig.subplot(0)

    val x1 = DenseVector((1 to temp.length).map(_.toDouble).toArray)
    val y1 = DenseVector(temp.toArray)

    val x2 = DenseVector((window to temp.length).map(_.toDouble).toArray)
    val y2 = DenseVector(smaValues.toArray)
    val y3 = DenseVector(wmaValues.toArray)
    val y4 = DenseVector(emaValues.toArray)

    p += plot(x1, y1)
    p += plot(x2, y2)
    p += plot(x2, y3)
    p += plot(x1, y4)

    p.xlabel = "Days"
    p.ylabel = "Temperature"
    p.title = "Daily Temperature with SMA, WMA and EMA"

    fig.refresh()
  }
}