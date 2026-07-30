import scala.io.Source

object PearsonPractical {

  def main(args: Array[String]): Unit = {

    val stream = getClass.getResourceAsStream("/winequalityN.csv")

    if (stream == null) {
      println("File not found!")
      return
    }

    val source = Source.fromInputStream(stream)

    val data = source.getLines().drop(1).flatMap { line =>
      val cols = line.split(",", -1)

      for {
        alcohol <- cols(11).trim.toDoubleOption
        quality <- cols(12).trim.toDoubleOption
      } yield (alcohol, quality)

    }.toList

    source.close()

    val (x, y) = data.unzip
    val n = x.length.toDouble

    val meanX = x.sum / n
    val meanY = y.sum / n

    val numerator = x.zip(y).map {
      case (a, q) => (a - meanX) * (q - meanY)
    }.sum

    val denominator = math.sqrt(
      x.map(a => math.pow(a - meanX, 2)).sum *
        y.map(q => math.pow(q - meanY, 2)).sum
    )

    val r = numerator / denominator

    val relation =
      if (r > 0) "Positive"
      else if (r < 0) "Negative"
      else "No Relationship"

    val df = n - 2
    val t = r * math.sqrt(df / (1 - r * r))
    val significant = math.abs(t) > 1.96

    println(s"Dataset Size: ${n.toInt}")
    println(f"Pearson Correlation (r): $r%.4f")
    println(s"Relationship: $relation")
    println(f"t-Statistic: $t%.4f")
    println(s"Significant at 5% level: $significant")
  }
}