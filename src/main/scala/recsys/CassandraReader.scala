package recsys

import org.apache.spark.{SparkContext, SparkConf}


object CassandraReader extends ada.ClasspathApp[Config](Some("recsys")){

  protected def run(config: Config, arguments: Array[String]) = {

    val cassConf = config.cassandra

    val sparkConf = new SparkConf()
      .set("spark.cassandra.connection.host", config.cassandra.servers.mkString(","))
      .set("spark.cassandra.connection.native.port", config.cassandra.port.toString)
      .setMaster("spark://10.0.180.5:7077")

    sparkConf.setAppName("Cassandra reader")

    implicit val sc = new SparkContext(sparkConf)
    implicit val session = config.cassandra.getSession("blocket")


    val numbers = sc.parallelize(List(1,2,3,4,5,6))
    println(numbers.reduce(_ + _))

  }


  
}
