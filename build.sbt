import AssemblyKeys._

assemblySettings

name := "defect-prediction"

organization := "io.prediction"

libraryDependencies ++= Seq(
 "org.apache.predictionio" %% "apache-predictionio-core" % "0.11.0-incubating" % "provided",
 "org.apache.spark" %% "spark-sql" % "1.5.2" % "provided",
  "commons-io"        % "commons-io"    % "2.4",
  "org.apache.spark" %% "spark-core"    % "1.2.0" % "provided",
  "org.apache.spark" %% "spark-mllib"   % "1.2.0" % "provided",
  "org.json4s"       %% "json4s-native" % "3.2.10")
