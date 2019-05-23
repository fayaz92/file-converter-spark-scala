package au.fayaz

import org.apache.spark.sql.{ DataFrame, SparkSession }
import org.slf4j.LoggerFactory

object Converter {

  def main(args: Array[String]): Unit = {

    val log = LoggerFactory.getLogger(this.getClass)

    log.debug("Initiating Spark Session to Convert CSV")

    val inputFile = args(0)
    val outputPath: String = args(1)
    val format: String = args(2)

    implicit val spark: SparkSession = Utils.initiateSpark()

    val inputFrame: DataFrame = Processor.readCSV(inputFile)
    Processor.writeOutput(inputFrame, outputPath, format)

    log.info("***** File Successfully Converted *****")

  }

}
