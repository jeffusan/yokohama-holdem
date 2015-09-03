package yokohama.holdem

import akka.actor.{ Actor, ExtendedActorSystem, Extension, ExtensionKey }
import akka.util.Timeout
import scala.concurrent.duration.FiniteDuration
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

object Settings extends ExtensionKey[Settings]

/**
  * This is an extension to the actor system,
  * providing a convenient way to add configuration
  */
class Settings(system: ExtendedActorSystem) extends Extension {

  case class Yokohama(app: App, game: Game, gameEngine: GameEngine)

  case class App(askTimeoutDuration: FiniteDuration) {
    implicit val askTimeout: Timeout = askTimeoutDuration
  }

  case class Game(betTimeout: FiniteDuration)

  case class GameEngine(maxPlayers: Int, askTimeoutDuration: FiniteDuration, startGameInterval: FiniteDuration) {
    implicit val askTimeout: Timeout = askTimeoutDuration
  }

  private val yokohama = system.settings.config.as[Yokohama]("yokohama")
  val app = yokohama.app
  val game = yokohama.game
  val gameEngine = yokohama.gameEngine

}

trait SettingsActor {

  this: Actor =>

  val settings: Settings =
    Settings(context.system)
}
