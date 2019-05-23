name := "converter"
organization := "au.com.fayaz"
version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.1" % Provided
libraryDependencies += "com.databricks" %% "spark-avro" % "4.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

dependencyOverrides += "commons-logging" % "commons-logging" % "1.2"
dependencyOverrides += "org.slf4j" % "slf4j-api" % "1.7.25"
dependencyOverrides += "org.apache.hadoop" % "hadoop-client" % "2.8.4"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.3.1" % Provided
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.4.1"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.3.1" % Provided
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.0" % Provided

