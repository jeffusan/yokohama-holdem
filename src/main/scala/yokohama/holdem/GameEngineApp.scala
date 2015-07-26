package yokohama.holdem

import akka.actor.{ ActorRef, ActorSystem }
import scala.annotation.tailrec
import scala.io.StdIn

/**
  * Currently not making use of the command loop
  */
object GameEngineApp extends Base with Terminal {

  override protected def initialize(system: ActorSystem, settings: Settings): ActorRef = {
    import settings.gameEngine._
    system.actorOf(GameEngine.props(askTimeout, startGameInterval, maxPlayers), GameEngine.name)
  }

  @tailrec
  override protected def commandLoop(system: ActorSystem, settings: Settings, top: ActorRef): Unit = {
    Command(StdIn.readLine().split(" ")) match {

      case Command.Shutdown =>
        system.shutdown()
      case Command.Unknown(command, message) =>
        system.log.warning("Unknown command {} ({})", command, message)
        commandLoop(system, settings, top)
      case other =>
        system.log.warning("I'm not responsible for this {}", other)
        commandLoop(system, settings, top)
    }
  }

}
