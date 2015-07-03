package recsys


import scala.concurrent.{Await, Future}

import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}



object CassandraReader extends ada.ClasspathApp[Config](Some("recsys")){

  protected def run(config: Config, arguments: Array[String]) = {

    val cassConf = config.cassandra

    val sparkConf = new SparkConf().setMaster("spark://spark0-analysis:7077")
      .set("spark.cassandra.connection.host", config.cassandra.servers.mkString(","))
      .set("spark.cassandra.connection.native.port", config.cassandra.port.toString)

    sparkConf.setAppName("Cassandra reader")

    implicit val sc = new SparkContext(sparkConf)
    implicit val session = config.cassandra.getSession("blocket")

    try{
      val blocketUserContentTable = sc.cassandraTable("blocket", "blocket_user_content")
      val firstRow = blocketUserContentTable.first()
      println("First record in blocket_user_content table",firstRow)
    }finally {
        sc.stop()
        session.close()
        session.getCluster.close()
    }
  }


  
}
