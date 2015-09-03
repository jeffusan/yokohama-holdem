package yokohama.holdem

import akka.actor.{ActorRef, ActorSystem}
import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.{Failure, Success, Try}

/**
 * The game engine app creates a game engine.
 */
object GameEngineApp extends Base with Terminal {

  override protected def initialize(system: ActorSystem, settings: Settings): ActorRef = {
    import settings.gameEngine._
    system.actorOf(GameEngine.props(askTimeout, startGameInterval, maxPlayers), GameEngine.name)
  }

  @tailrec
  override protected def commandLoop(system: ActorSystem, settings: Settings, topLevel: ActorRef): Unit = {
    // TODO: be more abstract with PlayerRepositoryApp#commandLoop

    print("GameEngine> ")
    Try(StdIn.readLine().trim().split(" ")) match {
      case Success(Array("")) =>
        commandLoop(system, settings, topLevel)
      case Success(input) =>
        Command(input) match {
          case Command.NoOperation =>
            commandLoop(system, settings, topLevel)
          case Command.Shutdown =>
            println("Shutting down game engine...")
            system.shutdown()
          case Command.Unknown(command, message) =>
            system.log.warning("Unknown command {} ({})", command, message)
            commandLoop(system, settings, topLevel)
          case other =>
            system.log.warning("I'm not responsible for this {}", other)
            commandLoop(system, settings, topLevel)
        }
      case Failure(e) =>
        println("Shutting down game engine...")
        system.shutdown()
    }
  }

}
