
import java.io.Serializable

import kafka.serializer.StringDecoder

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

import org.apache.spark.broadcast.Broadcast

import org.apache.spark.streaming._
import org.apache.spark.streaming.api.java._
import org.apache.spark.streaming.dstream._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._

import it.nerdammer.spark.hbase._

import model.Reading

import integration.KafkaMessageProducer

object Main {

	def main(args: Array[String]) : Unit  = {
		if (args.length < 5){
			println("General arguments: -name=<app_name> -duration=<duration> -consumeTopic=<consume_topic> -produceTopic=<produce_topic> -kafkaBrokerUrls=<broker_list>")
			System.exit(-1)
		}
		val opts = parseCmdLineArgs(args)

		val sparkConf = new SparkConf().setAppName(opts("name"))
		val streamingCtx = new StreamingContext(sparkConf, Milliseconds(opts("duration").toInt))

		val receiverTopics = Set(opts("consumeTopic"))
		val kafkaParams = Map[String, String]("metadata.broker.list" -> opts("kafkaBrokerUrls"))
		val sensorReadingDStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](streamingCtx, kafkaParams, receiverTopics)
		//broadcast the option Map
		val broadcastedOpts = streamingCtx.sparkContext.broadcast(opts)

		//process each sensor reading
		sensorReadingDStream.map(_._2).foreachRDD(processSensorReadingRDD(_, broadcastedOpts))

		// Start the computation
	    streamingCtx.start()
	    streamingCtx.awaitTermination()
	}

	/**
	 *
	 * process the rdd passed as parameter.
	 * processing involves
	 *	- convert to a data object
	 *	- conver tto a dataframe and perform ml classification on it
	 *	- send the classified data to kafka
	 *	- save the classified data to hbase cf enriched
	 *
	 * @param rdd
	 */
	def processSensorReadingRDD(rdd: RDD[String], bOpts: Broadcast[Map[String, String]]) : Unit ={
		val enrichedReading = rdd.mapPartition(partn => {
			//convert each string to Reading
			val opts = bOpts.value
			val kafkaDwnStreanProd = new KafkaMessageProducer()
			//convert to Reading RDD
			val readingRDD = partn map (Reading(_))
			//perform ML classification
			val classifiedReading = classify(readingRDD)
			//send classified Reading to Kafka downstream
			kafkaDwnStreanProd.sendMessage(classifiedReading, opts("kafkaBrokerUrls"), opts("produceTopic"))
			//return
			classifiedReading
		})

		//save to hbase
		saveToHBase(enrichedReading)
	}

	/**
	 *
	 * fit the rdd to a ml model and return the rdd with classes added to the rdd items
	 * @param rdd
	 * @return Iterator[Reading]
	 */
	def classify(rdd: Iterator[Reading]) : Iterator[Reading] = {

		rdd
	}

	/**
	 *
	 * save the items of this rdd to a hbase table
	 * @param rdd
	 */
	def saveToHBase(rdd: RDD[Reading]) : Unit = {
		rdd.map(i => i.toTuple)
		 	.toHBaseTable("sensor_data")
		    .toColumns("pDvId", "pts", "pPrs", "pTmp", "pVol", "pFlv", "pXbr", "iDvId", "ts", "prs", "tmp", "vol", "flv", "xbr", "dst", "clz", "incd")
		    .inColumnFamily("clsifd")
		    .save()
	}

	/**
	*
	* @param args -
	*/
	def parseCmdLineArgs(args : Array[String]) = {
			args.map(arg => {
				val parts = arg.split("=")
				(parts(0).substring(1), parts(1))
			}).toMap
	}
}
