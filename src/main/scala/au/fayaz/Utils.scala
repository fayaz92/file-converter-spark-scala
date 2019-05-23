package au.fayaz

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Utils {

  def initiateSpark(): SparkSession = {
    val conf = new SparkConf().setAppName("Lambda with Spark").setMaster("local[*]")
    val spark = SparkSession.
      builder.
      master("local[*]").
      appName("File Converter").
      getOrCreate()
    spark.conf.set("spark.debug.maxToStringFields", "5000")
    spark
  }

}
