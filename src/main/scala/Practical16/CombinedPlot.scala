import breeze.linalg.DenseVector
import breeze.plot._

object CombinedPlot extends App {

  // Airline Passenger Satisfaction Dataset
  // X-axis: Flight Distance
  // Y-axis: Departure Delay

  val flightDistance = DenseVector(
    1000.0, 1200.0, 1500.0, 1800.0, 2000.0,
    2200.0, 2500.0, 2800.0, 3000.0, 3500.0
  )

  val departureDelay = DenseVector(
    5.0, 10.0, 8.0, 15.0, 12.0,
    20.0, 18.0, 25.0, 22.0, 30.0
  )

  // Create figure
  val fig = Figure("Airline Passenger Satisfaction")

  // Create subplot
  val plt = fig.subplot(0)

  // Add line plot
  plt += plot(
    flightDistance,
    departureDelay,
    name = "Delay Trend"
  )

  // Add scatter plot with required parameters
  plt += scatter(
    flightDistance,
    departureDelay,
    size = _ => 8.0,
    colors = _ => java.awt.Color.BLUE,
    labels = _ => "",
    tips = i => s"Flight Distance: ${flightDistance(i)}, Delay: ${departureDelay(i)}",
    name = "Passenger Data"
  )

  // Title
  plt.title = "Flight Distance vs Departure Delay"

  // X-axis label
  plt.xlabel = "Flight Distance"

  // Y-axis label
  plt.ylabel = "Departure Delay (Minutes)"

  // Show legend
  plt.legend = true
}