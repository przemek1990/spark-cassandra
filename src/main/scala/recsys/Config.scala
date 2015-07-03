package recsys

import java.io.File
import java.util.{Map => JMap}

import com.datastax.driver.core.Cluster
import org.constretto.Constretto
import org.constretto.Constretto._
import org.constretto.Converter._

object Config {

  implicit val cassandraConfConverter = fromObject { obj =>
    val servers = obj[List[String]]("servers")
    val port = obj.get[Int]("port").getOrElse(9042)
    CassandraConf(servers, port)
  }

  implicit val configConverter = fromObject { obj =>
    val cassandra = obj[CassandraConf]("cassandra")
    Config(cassandra)
  }
}


object Sites {

  implicit val cassandraSiteConverter = fromObject{ obj =>
    CassandraSite(obj[String]("keyspace"), obj[String]("table-prefix"))
  }

  implicit val siteConverter = fromObject{ obj =>
    SiteConfig(
      obj[String]("name"),
      obj[CassandraSite]("cassandra")
    )
  }

  def apply(file: File): Sites = {
    val con = Constretto(List(json(file.toURI.toString, "sites")))
    Sites(con[Map[String, SiteConfig]]("sites"))
  }
}


case class CassandraConf(servers: List[String], port: Int) {
  lazy val cluster = {
    val builder = Cluster.builder()
    servers.foreach(builder.addContactPoint)
    builder.withPort(port)
      .withoutJMXReporting()
      .withoutMetrics()
      .build()
  }

  def getSession(ks: String) = cluster.connect(ks)
}

case class Config(cassandra: CassandraConf)

case class CassandraSite(keyspace: String, tablePrefix: String)

case class SiteConfig(name: String, cassandra: CassandraSite)

case class Sites(underlying: Map[String, SiteConfig]) {
  def get(name: String): Option[SiteConfig] = underlying.get(name)
}
