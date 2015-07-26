name := "Yokohama Holdem"

version := "0.0.1"

scalaVersion := "2.11.6"

libraryDependencies ++= Dependencies.holdem


addCommandAlias("pr", "runMain yokohama.holdem.PlayerRepositoryApp -Dakka.remote.netty.tcp.port=2789 -Dakka.cluster.roles.0=player-repo")
