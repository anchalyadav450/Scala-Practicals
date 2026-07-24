package Practical13

import breeze.plot._
import scala.io.Source

object ScatterPlot {

  def main(args: Array[String]): Unit = {

    val fileName =
      "src/main/scala/Practical13/airline_passenger_satisfaction.csv"

    // Read CSV file
    val source = Source.fromFile(fileName)
    val lines = source.getLines().toList
    source.close()

    // Remove header
    val data = lines.drop(1)

    // Store data
    val flightDistance = scala.collection.mutable.ArrayBuffer[Double]()
    val passengerAge = scala.collection.mutable.ArrayBuffer[Double]()
    val customerType = scala.collection.mutable.ArrayBuffer[String]()

    // Read first 100 records
    data.take(100).foreach { line =>

      val columns = line.split(",", -1)

      try {
        // Customer Type = column 3
        // Age = column 4
        // Flight Distance = column 7

        val customer = columns(3).trim
        val age = columns(4).trim.toDouble
        val distance = columns(7).trim.toDouble

        customerType += customer
        passengerAge += age
        flightDistance += distance

      } catch {
        case _: Exception =>
          // Skip invalid rows
      }
    }

    // Separate Loyal Customers
    val loyalData =
      flightDistance.zip(passengerAge).zip(customerType)
        .filter(_._2 == "Loyal Customer")
        .map(x => (x._1._1, x._1._2))

    // Separate disloyal Customers
    val disloyalData =
      flightDistance.zip(passengerAge).zip(customerType)
        .filter(_._2 == "disloyal Customer")
        .map(x => (x._1._1, x._1._2))

    // Create Figure
    val figure = Figure()

    val plot = figure.subplot(0)

    // Red points - Loyal Customers
    if (loyalData.nonEmpty) {

      val loyalX = loyalData.map(_._1).toSeq
      val loyalY = loyalData.map(_._2).toSeq

      plot += scatter(
        loyalX,
        loyalY,
        _ => 8.0,
        _ => java.awt.Color.RED
      )
    }

    // Green points - disloyal Customers
    if (disloyalData.nonEmpty) {

      val disloyalX = disloyalData.map(_._1).toSeq
      val disloyalY = disloyalData.map(_._2).toSeq

      plot += scatter(
        disloyalX,
        disloyalY,
        _ => 8.0,
        _ => java.awt.Color.GREEN
      )
    }

    // Axis labels
    plot.xlabel = "Flight Distance"
    plot.ylabel = "Passenger Age"

    // Display information
    println("Scatter plot created successfully!")
    println("Red points = Loyal Customer")
    println("Green points = disloyal Customer")
  }
}