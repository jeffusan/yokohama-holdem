import sbt._

object Version {
  val akka           = "2.3.6"
  val scalaTest      = "2.2.2"
  val scalaCheck     = "1.12.1"
  val logback        = "1.1.2"
  val combinators    = "2.11.0-M4" 
  val scopt          = "3.3.0"
  val scalaz         = "7.1.3"
  val ficus          = "1.1.2"
}

object Library {

  val scalaTest          = "org.scalatest"     %% "scalatest"                % Version.scalaTest
  val scalaCheck         = "org.scalacheck"    %% "scalacheck"               % Version.scalaCheck
  val akkaActor          = "com.typesafe.akka" %% "akka-actor"               % Version.akka
  val akkaCluster        = "com.typesafe.akka" %% "akka-cluster"             % Version.akka
  val akkaContrib        = "com.typesafe.akka" %% "akka-contrib"             % Version.akka
  val akkaSlf4j          = "com.typesafe.akka" %% "akka-slf4j"               % Version.akka
  val akkaTestkit        = "com.typesafe.akka" %% "akka-testkit"             % Version.akka
  val logbackClassic     = "ch.qos.logback"    %  "logback-classic"          % Version.logback
  val combinatorsParsers = "org.scala-lang"    %  "scala-parser-combinators" % Version.combinators
  val scopt              = "com.github.scopt"  %% "scopt"                    % Version.scopt
  val scalaz             = "org.scalaz"        %% "scalaz-core"              % Version.scalaz
  val ficus              = "net.ceedubs"       %% "ficus"                    % Version.ficus
}

object Dependencies {

  import Library._

  val holdem = List(
    akkaActor,
    akkaCluster,
    akkaContrib,
    akkaSlf4j,
    akkaTestkit,
    logbackClassic,
    combinatorsParsers,
    scopt,
    scalaz,
    ficus,
    scalaTest       %    "test",
    scalaCheck      %    "test"
  )
}
