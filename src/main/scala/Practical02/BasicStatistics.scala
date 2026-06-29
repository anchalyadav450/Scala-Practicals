package Practical02

object BasicStatistics {

  def main(args: Array[String]): Unit = {

    val numbers = List(10, 20, 30, 40, 50, 20, 30)

    val mean = numbers.sum.toDouble / numbers.length

    val sortedNumbers = numbers.sorted
    val n = sortedNumbers.length

    val median =
      if (n % 2 == 0)
        (sortedNumbers(n / 2 - 1) + sortedNumbers(n / 2)) / 2.0
      else
        sortedNumbers(n / 2)

    val frequency = numbers.groupBy(identity).view.mapValues(_.size).toMap
    val maxFrequency = frequency.values.max
    val mode = frequency.filter(_._2 == maxFrequency).keys

    println("Numbers : " + numbers)
    println("Mean    : " + mean)
    println("Median  : " + median)
    println("Mode    : " + mode.mkString(", "))
  }
}