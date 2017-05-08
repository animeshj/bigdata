package default

import java.io.File
import java.sql.{DriverManager, ResultSet}
import java.util.Properties
import org.apache.hadoop.fs.{FileSystem, FileUtil, Path}
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.mapreduce.Job
import org.apache.spark.SparkContext._

import java.util.concurrent.{Callable, FutureTask}

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer
import org.apache.spark.storage.StorageLevel

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Future
import org.apache.spark.rdd.JdbcRDD
import java.sql.{Connection, DriverManager, ResultSet}
import org.apache.spark.storage.StorageLevel
import org.apache.spark.rdd.JdbcRDD
import java.sql.{Connection, DriverManager, ResultSet}
import org.apache.spark.storage.StorageLevel
//import com.databricks.spark.avro._
import org.apache.spark.{SparkConf, SparkContext}


import org.apache.hadoop.io.Text
import org.apache.hadoop.mapred.{FileSplit, TextInputFormat}
import org.apache.spark.rdd.HadoopRDD
import org.apache.hadoop.conf.Configuration


object FileDelta extends java.io.Serializable{

def main(args : Array[String]): Unit={

	
	println("spark-submit --class default.FileDelta jarname input_files|input_dirs output_dir")
	
	//if(args.size<2)
	//	println("invalid arguments")
	
	println(args(0))
	println(args(1))
	
	val conf = new SparkConf().setAppName(s"$args(0)_DELTA_$args(1)")
	val sc = new SparkContext(conf)
	
	val hadoopConfig = new Configuration(sc.hadoopConfiguration)
	val rdd_file = sc.newAPIHadoopFile(args(0), classOf[org.data.hadoop.NlinesFileName]	, classOf[Text], classOf[Text], hadoopConfig )
	val grpd = rdd_file.map(x=>(x._2,x._1)).groupByKey()
	val setd = grpd.map(x=>(x._1,x._2.toSet))
	val filtered = setd.filter(x=>x._2.size==1).map(x=>(x._1.toString+"~"+x._2.head))
	filtered.saveAsTextFile(args(1))

	sc.stop()				

	}	
}