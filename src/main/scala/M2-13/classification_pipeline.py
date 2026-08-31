from pyspark.sql import SparkSession
from pyspark.ml import Pipeline
from pyspark.ml.feature import StringIndexer, VectorAssembler
from pyspark.ml.classification import LogisticRegression
from pyspark.ml.evaluation import MulticlassClassificationEvaluator

# Create Spark session
spark = SparkSession.builder \
    .appName("StudentResultClassification") \
    .master("local[*]") \
    .getOrCreate()

# Read the CSV file
df = spark.read.csv(
    "students_result.csv",
    header=True,
    inferSchema=True
)

# Display the original data
print("Original Data:")
df.show()

# Step 1: Convert the text label (Pass/Fail) into a numeric label
label_indexer = StringIndexer(inputCol="Result", outputCol="label")

# Step 2: Combine feature columns into a single vector column
assembler = VectorAssembler(
    inputCols=["Marks", "Attendance"],
    outputCol="features"
)

# Step 3: Define the Logistic Regression model
lr = LogisticRegression(featuresCol="features", labelCol="label", maxIter=20)

# Step 4: Chain everything into a Pipeline
pipeline = Pipeline(stages=[label_indexer, assembler, lr])

# Step 5: Split data into training and test sets
train_data, test_data = df.randomSplit([0.8, 0.2], seed=42)

# Step 6: Train the model
model = pipeline.fit(train_data)

# Step 7: Make predictions on the test set
predictions = model.transform(test_data)

print("Predictions:")
predictions.select("Name", "Marks", "Attendance", "Result", "prediction").show()

# Step 8: Evaluate accuracy
evaluator = MulticlassClassificationEvaluator(
    labelCol="label",
    predictionCol="prediction",
    metricName="accuracy"
)
accuracy = evaluator.evaluate(predictions)
print(f"Test Accuracy = {accuracy}")

# Stop Spark
spark.stop()
