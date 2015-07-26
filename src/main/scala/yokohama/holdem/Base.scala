package yokohama.holdem

import akka.actor.{ ActorRef, ActorSystem }

/**
  * Base is a unified means of starting an actor system with
  * a command loop
  */
abstract class Base {

  /**
    * Main entry into the application.
    * If options need to be passed in, this is where it should happen.
    */
  def main(args: Array[String]): Unit = {

    val system = ActorSystem("holdem")
    val settings = Settings(system)
    val topLevel = initialize(system, settings)
    system.log.info(f"{} running%nEnter commands into the terminal`s` or `shutdown`", getClass.getSimpleName)
    commandLoop(system, settings, topLevel)
    system.awaitTermination()
  }

  /**
    * creates the first actorref for the system
    */
  protected def initialize(system: ActorSystem, settings: Settings): ActorRef =
    system.deadLetters

  /**
    * Command loop receives commands and entered into the running system,
    * parses them in an application-specific way
    */
  protected def commandLoop(system: ActorSystem, settings: Settings, topLevel: ActorRef): Unit
}
