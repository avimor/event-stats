name := "event-stats"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.6.5",
  "com.typesafe.akka" %% "akka-http" % "10.1.11",
  "com.typesafe.play" %% "play-json" % "2.8.1",
  "de.heikoseeberger" %% "akka-http-play-json" % "1.32.0",
)
