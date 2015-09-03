name := "Yokohama Holdem"

version := "0.0.1"

scalaVersion := "2.11.6"

libraryDependencies ++= Dependencies.holdem

initialCommands := """|import yokohama.holdem._
                      |import akka.actor._
                      |import akka.actor.ActorDSL._
                      |import akka.cluster._
                      |import akka.cluster.routing._
                      |import akka.routing._
                      |import akka.util._
                      |import com.typesafe.config._
                      |import scala.concurrent._
                      |import scala.concurrent.duration._""".stripMargin


addCommandAlias("ge", "runMain yokohama.holdem.GameEngineApp -Dakka.remote.netty.tcp.port=12551 -Dakka.cluster.roles=[game-engine]")

addCommandAlias("pr", "runMain yokohama.holdem.PlayerRepositoryApp -Dakka.remote.netty.tcp.port=12552 -Dakka.cluster.roles=[player-repository]")

