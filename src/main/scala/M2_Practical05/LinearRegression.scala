package M2_Practical05

import breeze.linalg.{DenseMatrix, DenseVector, inv}
import com.github.tototoshi.csv._
import java.io.File

object LinearRegression {

  def main(args: Array[String]): Unit = {

    // Read CSV dataset
    val file = new File(
      "src/main/scala/M2_Practical05/study_hours_scores_regression.csv"
    )

    val reader = CSVReader.open(file)
    val data = reader.all()
    reader.close()

    // Separate header and data
    val header = data.head
    val rows = data.tail

    // Find column indexes
    val hoursIndex = header.indexOf("Hours")
    val scoresIndex = header.indexOf("Scores")

    // Import actual data from CSV
    val x = DenseVector(
      rows.map(row => row(hoursIndex).trim.toDouble).toArray
    )

    val y = DenseVector(
      rows.map(row => row(scoresIndex).trim.toDouble).toArray
    )

    // Display original dataset
    println("Original Dataset")
    println("------------------------------")
    println(s"Hours  : $x")
    println(s"Scores : $y")

    // Create design matrix
    val ones = DenseVector.ones[Double](x.length)

    val X = DenseMatrix.horzcat(
      ones.asDenseMatrix.t,
      x.asDenseMatrix.t
    )

    // Linear Regression using Ordinary Least Squares
    // Beta = (X'X)^(-1) X'y
    val coefficients =
      inv(X.t * X) * X.t * y

    // Get regression coefficients
    val intercept = coefficients(0)
    val slope = coefficients(1)

    println("\nLinear Regression Coefficients")
    println("------------------------------")
    println(f"Intercept : $intercept%.4f")
    println(f"Slope     : $slope%.4f")

    // Predict score for 6 study hours
    val newHours = 6.0

    val predictedScore =
      intercept + slope * newHours

    println("\nPrediction")
    println("------------------------------")
    println(s"Study Hours     : $newHours")
    println(f"Predicted Score : $predictedScore%.2f")

    // Calculate predictions
    val predictions = X * coefficients

    // Calculate mean of scores
    val meanY =
      y.data.sum / y.length

    // Total Sum of Squares
    val ssTotal =
      y.data.map(value =>
        math.pow(value - meanY, 2)
      ).sum

    // Residual Sum of Squares
    val ssResidual =
      y.data.zip(predictions.data).map {
        case (actual, predicted) =>
          math.pow(actual - predicted, 2)
      }.sum

    // Calculate R-squared
    val rSquared =
      1.0 - (ssResidual / ssTotal)

    println("\nModel Performance")
    println("------------------------------")
    println(f"R-squared : $rSquared%.4f")
  }
}