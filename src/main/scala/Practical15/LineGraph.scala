package Practical15

import breeze.linalg.DenseVector
import breeze.plot._

object LineGraph {

  def main(args: Array[String]): Unit = {

    // Data from Flight Passenger Satisfaction dataset
    // Age values
    val age = DenseVector(
      13.0, 25.0, 26.0, 25.0, 61.0,
      26.0, 47.0, 52.0, 41.0, 20.0,
      24.0, 12.0, 53.0, 33.0, 26.0,
      13.0, 26.0, 41.0, 45.0, 38.0
    )

    // Flight Distance values
    val flightDistance = DenseVector(
      460.0, 235.0, 1142.0, 562.0, 214.0,
      1180.0, 1276.0, 2035.0, 853.0, 1061.0,
      1182.0, 308.0, 834.0, 946.0, 453.0,
      468.0, 2123.0, 2075.0, 2486.0, 460.0
    )

    // Create plot
    val figure = Figure("Flight Distance Trend by Passenger Age")

    // Create line graph using plot
    figure.subplot(0) += plot(
      age,
      flightDistance,
      lines = true,
      shapes = false
    )

    // Label axes
    figure.subplot(0).xlabel = "Passenger Age"
    figure.subplot(0).ylabel = "Flight Distance"

    // Display graph
    figure.refresh()

    println("Line graph created successfully!")
  }
}