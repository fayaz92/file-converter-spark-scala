package au.fayaz

import org.apache.spark.sql.{ DataFrame, SparkSession }

object Processor {

  def readCSV(inputPath: String)(implicit spark: SparkSession): DataFrame = {
    val readData = spark.read.format("csv").option("header", "true").load(inputPath)
    readData
  }

  def readAvro(inputPath: String)(implicit spark: SparkSession): DataFrame = {
    val readAvroData = spark.read.format("com.databricks.spark.avro").load(inputPath)
    readAvroData
  }

  def readParquet(inputPath: String)(implicit spark: SparkSession): DataFrame = {
    val readData = spark.read.format("parquet").load(inputPath)
    readData
  }

  def writeOutput(dataframe: DataFrame, outputPath: String, format: String): Unit = {
    if (format == "parquet") {
      dataframe.write.option("compression", "snappy").mode("overwrite")
        .parquet(outputPath)
    } else
      dataframe
        .write
        .format("com.databricks.spark.avro").mode("overwrite")
        .save(outputPath)
  }

}
