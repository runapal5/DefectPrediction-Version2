import AssemblyKeys._

assemblySettings

name := "defect-prediction"

organization := "io.prediction"

scalaVersion := "2.11.6"


libraryDependencies ++= Seq(
 "org.apache.predictionio" %% "apache-predictionio-core" % "0.11.0-incubating" % "provided",
  "commons-io"        % "commons-io"    % "2.4",
  "org.apache.spark" %% "spark-core"    % "2.2.0" % "provided",
  "org.apache.spark" %% "spark-mllib"   % "2.2.0" % "provided",
  "org.json4s"       %% "json4s-native" % "3.2.10")
