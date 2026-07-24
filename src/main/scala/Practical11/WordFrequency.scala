import scala.io.Source

object WordFrequency {

  def main(args: Array[String]): Unit = {

    // File path
    val fileName = "src/main/scala/Practical11/scala_learning.txt"

    // Read text file
    val source = Source.fromFile(fileName)
    val text = source.getLines().mkString(" ")
    source.close()

    // Tokenize words
    val words = text
      .toLowerCase
      .replaceAll("[^a-zA-Z ]", "")
      .split("\\s+")
      .filter(_.nonEmpty)

    // Count frequency
    val wordCounts = words.groupBy(identity).map {
      case (word, occurrences) =>
        (word, occurrences.size)
    }

    // Display result
    println("Word Frequencies")
    println("----------------------------")

    wordCounts.foreach {
      case (word, count) =>
        println(word + " : " + count)
    }

  }

}