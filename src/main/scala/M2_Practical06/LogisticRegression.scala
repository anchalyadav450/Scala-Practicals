package p06

import breeze.linalg._
import com.github.tototoshi.csv._
import java.io.File

object LogisticRegression {

  def sigmoid(z: Double): Double = {
    1.0 / (1.0 + math.exp(-z))
  }

  def main(args: Array[String]): Unit = {

    // ---------------------------------------------------------
    // 1. Read CSV Dataset
    // ---------------------------------------------------------

    val file = new File(
      "src/main/scala/M2_Practical06/Social_Network_Ads.csv"
    )

    val reader = CSVReader.open(file)
    val data = reader.all()
    reader.close()

    // Separate header and rows
    val header = data.head
    val rows = data.tail

    // Find column indexes from actual CSV headers
    val ageIndex = header.indexOf("Age")
    val salaryIndex = header.indexOf("EstimatedSalary")
    val purchasedIndex = header.indexOf("Purchased")

    // Check columns
    if (ageIndex == -1 || salaryIndex == -1 || purchasedIndex == -1) {
      println("Required columns were not found in the CSV file.")
      println("Required: Age, EstimatedSalary, Purchased")
      return
    }

    // ---------------------------------------------------------
    // 2. Convert CSV data into numerical values
    // ---------------------------------------------------------

    val validRows = rows.filter { row =>
      row.length > math.max(ageIndex, math.max(salaryIndex, purchasedIndex)) &&
        row(ageIndex).trim.nonEmpty &&
        row(salaryIndex).trim.nonEmpty &&
        row(purchasedIndex).trim.nonEmpty
    }

    val age = validRows.map(row => row(ageIndex).trim.toDouble)
    val salary = validRows.map(row => row(salaryIndex).trim.toDouble)
    val purchased = validRows.map(row => row(purchasedIndex).trim.toDouble)

    val n = validRows.length

    println("==============================================")
    println("       LOGISTIC REGRESSION")
    println("       Social Network Ads Dataset")
    println("==============================================")

    println(s"Total Records : $n")
    println("Features      : Age, EstimatedSalary")
    println("Target        : Purchased (0 / 1)")

    // ---------------------------------------------------------
    // 3. Prepare features
    // ---------------------------------------------------------

    // Standardize features so gradient descent works smoothly
    val ageMean = age.sum / n
    val salaryMean = salary.sum / n

    val ageStd = math.sqrt(
      age.map(x => math.pow(x - ageMean, 2)).sum / n
    )

    val salaryStd = math.sqrt(
      salary.map(x => math.pow(x - salaryMean, 2)).sum / n
    )

    val ageScaled = age.map(x => (x - ageMean) / ageStd)
    val salaryScaled = salary.map(x => (x - salaryMean) / salaryStd)

    // ---------------------------------------------------------
    // 4. Create feature matrix using Breeze
    // ---------------------------------------------------------

    val X = DenseMatrix.zeros[Double](n, 3)

    for (i <- 0 until n) {
      X(i, 0) = 1.0
      X(i, 1) = ageScaled(i)
      X(i, 2) = salaryScaled(i)
    }

    val y = DenseVector(purchased.toArray)

    // ---------------------------------------------------------
    // 5. Logistic Regression using Gradient Descent
    // ---------------------------------------------------------

    var weights = DenseVector.zeros[Double](3)

    val learningRate = 0.1
    val iterations = 10000

    for (_ <- 0 until iterations) {

      val scores = X * weights

      val probabilities = DenseVector(
        scores.data.map(sigmoid)
      )

      val error = probabilities - y

      val gradient =
        (X.t * error) / n.toDouble

      weights = weights - (learningRate * gradient)
    }

    // ---------------------------------------------------------
    // 6. Display model coefficients
    // ---------------------------------------------------------

    println()
    println("Model Coefficients")
    println("----------------------------------------------")

    println(f"Intercept          : ${weights(0)}%.4f")
    println(f"Age coefficient    : ${weights(1)}%.4f")
    println(f"Salary coefficient : ${weights(2)}%.4f")

    // ---------------------------------------------------------
    // 7. Make predictions
    // ---------------------------------------------------------

    val scores = X * weights

    val probabilities = DenseVector(
      scores.data.map(sigmoid)
    )

    val predictions = DenseVector.zeros[Double](n)

    for (i <- 0 until n) {
      if (probabilities(i) >= 0.5)
        predictions(i) = 1.0
      else
        predictions(i) = 0.0
    }

    // ---------------------------------------------------------
    // 8. Calculate Accuracy
    // ---------------------------------------------------------

    var correct = 0

    for (i <- 0 until n) {
      if (predictions(i) == y(i)) {
        correct += 1
      }
    }

    val accuracy = correct.toDouble / n * 100

    println()
    println("Classification Results")
    println("----------------------------------------------")
    println(s"Correct Predictions : $correct")
    println(s"Total Predictions   : $n")
    println(f"Accuracy            : $accuracy%.2f%%")

    // ---------------------------------------------------------
    // 9. Display sample predictions
    // ---------------------------------------------------------

    println()
    println("Sample Predictions")
    println("----------------------------------------------")
    println(
      f"${"Age"}%-8s ${"Salary"}%-12s ${"Actual"}%-10s ${"Probability"}%-15s ${"Predicted"}"
    )

    println("----------------------------------------------")

    val sampleSize = math.min(10, n)

    for (i <- 0 until sampleSize) {

      println(
        f"${age(i)}%-8.0f " +
          f"${salary(i)}%-12.0f " +
          f"${y(i)}%-10.0f " +
          f"${probabilities(i)}%-15.4f " +
          f"${predictions(i)}%.0f"
      )
    }

    println("----------------------------------------------")

    // ---------------------------------------------------------
    // 10. Predict a new customer
    // ---------------------------------------------------------

    val newAge = 30.0
    val newSalary = 50000.0

    val newAgeScaled =
      (newAge - ageMean) / ageStd

    val newSalaryScaled =
      (newSalary - salaryMean) / salaryStd

    val newZ =
      weights(0) +
        weights(1) * newAgeScaled +
        weights(2) * newSalaryScaled

    val newProbability = sigmoid(newZ)

    val newPrediction =
      if (newProbability >= 0.5) 1 else 0

    println()
    println("New Customer Prediction")
    println("----------------------------------------------")
    println(s"Age               : $newAge")
    println(s"Estimated Salary   : $newSalary")
    println(f"Purchase Probability : $newProbability%.4f")

    if (newPrediction == 1)
      println("Prediction          : Purchased (1)")
    else
      println("Prediction          : Not Purchased (0)")

    println("----------------------------------------------")
  }
}