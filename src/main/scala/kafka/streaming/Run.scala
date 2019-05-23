package kafka.streaming

import java.net.InetAddress

import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.{ CassandraConnector, CassandraConnectorConf }
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{ Seconds, StreamingContext }
import utils.SparkJob

object Run extends SparkJob {

  override def run(implicit session: SparkSession): Unit = {

    val kafkaBrokerHost = "192.168.99.100"
    val kafkaBrokerPort = 9092
    val cassandraPort = 9042
    val keySpaceName = "test"
    val topicName = "test"
    //    val topics = Array("test","test2")
    val groupIdName = "test"
    val tableName = "pokemon"
    val offset = "latest"
    val cassandraLocalHost = "0.0.0.0"
    val topics = Array(topicName)

    val streamingContext = new StreamingContext(session.sparkContext, Seconds(2))
    val cassandraConnectorConfiguration = new CassandraConnectorConf(Set(InetAddress.getByName(cassandraLocalHost)), cassandraPort)
    implicit val cassandraConnector: CassandraConnector = new CassandraConnector(cassandraConnectorConfiguration)

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> s"$kafkaBrokerHost:$kafkaBrokerPort",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> groupIdName,
      "auto.offset.reset" -> offset,
      "enable.auto.commit" -> (false: java.lang.Boolean))

    val stream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams))

    stream.foreachRDD(record => {
      val mapped = record.map(r => Tuple1(r.value()))

      mapped.saveToCassandra(keySpaceName, tableName, SomeColumns("pokemon"))
      record.foreach(r => {
        println(r.value())
      })
    })

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
//kafka-topics.sh --create --zookeeper
// pass message to the producer kafka-console-producer.sh --broker-list
//landoop/fast-data-dev:latest