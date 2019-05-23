package utils

import org.apache.spark.sql.SparkSession

trait SparkJob {

  def run(implicit session: SparkSession): Unit

}
