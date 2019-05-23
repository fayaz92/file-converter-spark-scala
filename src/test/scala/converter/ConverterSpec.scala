package converter

import au.fayaz.{ Processor, Utils }
import org.apache.spark.sql.{ DataFrame, SparkSession }
import org.scalatest.WordSpec
import org.slf4j.LoggerFactory

class ConverterSpec extends WordSpec {

  implicit val spark: SparkSession = Utils.initiateSpark()
  val log = LoggerFactory.getLogger(this.getClass)

  import spark.implicits._

  val colNames: Seq[String] = Seq("colA", "colB",
    "colC")

  val df1: DataFrame = Seq(
    ("ID001", "Mr. Abc", "Melbourne"),
    ("ID002", "Ms. Xyz", "Sydney"))
    .toDF(colNames: _*)

  "should check nothing " in {
    log.info("Running first test case - check nothing")
    assert(1 == 1)
  }

  "should check it reads csv" in {
    val csvPath = "src/test/resources/testData/data.csv"
    val function = Processor.readCSV(csvPath)
    log.info("Running second test case - the test dataset")
    assert(function.count() >= 1)
  }

  "should check it writes AVRO" in {
    val avroPath = "src/test/resources/testData/avroWrite/"
    Processor.writeOutput(df1, avroPath, "avro")
    log.info("Running third test case - writing avro successful")
    val result = Processor.readAvro(avroPath + "*.avro")
    assert(result.count() >= 1)
  }

  "should check it writes Parquet" in {
    val path = "src/test/resources/testData/parquetWrite/"
    Processor.writeOutput(df1, path, "parquet")
    log.info("Running fourth test case - writing parquet successful")
    val result = Processor.readParquet(path + "*.parquet")
    assert(result.count() >= 1)
  }

}