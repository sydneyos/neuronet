organization in ThisBuild := "com.weenet"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"
lagomKafkaEnabled in ThisBuild := false
lagomKafkaAddress in ThisBuild := "localhost:9092"
// lagomKafkaPropertiesFile in ThisBuild := Some((baseDirectory in ThisBuild).value / "project" / "kafka-server.properties")

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `neuronet` = (project in file("."))
  .aggregate(`neuronet-api`, `neuronet-impl`, `neuronet-stream-api`, `neuronet-stream-impl`)

lazy val `neuronet-api` = (project in file("neuronet-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `neuronet-impl` = (project in file("neuronet-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`neuronet-api`)

lazy val `neuronet-stream-api` = (project in file("neuronet-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `neuronet-stream-impl` = (project in file("neuronet-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`neuronet-stream-api`, `neuronet-api`)
