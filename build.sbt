import schibsted.sbt._

val sparkVersion = "1.3.1"

lazy val root = Projects().sparkModule("com.schibsted.grouptech", "recsys-spark-cassandra", "root", Some(file(".")))(sparkVersion).settings(
  scalaVersion := "2.11.7",
  libraryDependencies ++= Seq(
    "com.schibsted.grouptech" %% "ada-app-base" % "0.5.2",
    "com.schibsted.grouptech" %% "recsys-cassandra-records" % "0.5.2",
    "com.datastax.spark" %% "spark-cassandra-connector" % "1.3.0-M1" excludeAll(ExclusionRule("org.apache.spark"), ExclusionRule("com.datastax.cassandra")),
    "com.datastax.cassandra" % "cassandra-driver-core" % "2.1.5" exclude("com.google.guava", "guava"),
    "org.constretto" %% "constretto-scala" % "1.1",
    "org.scalatest" %% "scalatest" % "2.2.2" % "test"
  ),
  buildInfoPackage := "recsys",
  excludeDependencies += "log4j",
  excludeDependencies += "org.slf4j" % "slf4j-log4j12"
)