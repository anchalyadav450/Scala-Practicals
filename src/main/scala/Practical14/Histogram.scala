package Practical14

import breeze.linalg._
import breeze.plot._

object Histogram {

  def main(args: Array[String]): Unit = {

    // Flight Distance values from Airline Passenger Satisfaction dataset
    val flightDistance = DenseVector(
      460.0, 235.0, 1142.0, 562.0, 214.0,
      1180.0, 1276.0, 2035.0, 853.0, 1061.0,
      1182.0, 308.0, 834.0, 946.0, 453.0,
      486.0, 2123.0, 2075.0, 2486.0, 460.0,
      1174.0, 208.0, 752.0, 2139.0, 452.0,
      719.0, 1561.0, 315.0, 3347.0, 2342.0
    )

    // Create figure
    val fig = Figure("Histogram of Flight Distance")

    // Create three plots
    val p1 = fig.subplot(0)
    val p2 = fig.subplot(1)
    val p3 = fig.subplot(2)

    // Histogram with 5 bins
    p1 += hist(flightDistance, 5)
    p1.title = "Histogram with 5 bins"
    p1.xlabel = "Flight Distance"
    p1.ylabel = "Frequency"

    // Histogram with 10 bins
    p2 += hist(flightDistance, 10)
    p2.title = "Histogram with 10 bins"
    p2.xlabel = "Flight Distance"
    p2.ylabel = "Frequency"

    // Histogram with 20 bins
    p3 += hist(flightDistance, 20)
    p3.title = "Histogram with 20 bins"
    p3.xlabel = "Flight Distance"
    p3.ylabel = "Frequency"

    // Display the figure
    fig.refresh()

    println("Histogram created successfully!")
    println("First histogram: 5 bins")
    println("Second histogram: 10 bins")
    println("Third histogram: 20 bins")
  }
}