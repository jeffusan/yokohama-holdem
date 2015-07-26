package yokohama.holdem

import akka.actor.{ Actor, ExtendedActorSystem, Extension, ExtensionKey }
import akka.util.Timeout
import scala.concurrent.duration.{ Duration, FiniteDuration, MILLISECONDS => Millis }

object Settings extends ExtensionKey[Settings]

/**
  * This is an extension to the actor system,
  * providing a convenient way to add configuration
  */
class Settings(system: ExtendedActorSystem) extends Extension {

  object app {
    implicit val askTimeout: Timeout =
      Duration(yokohama.getDuration("app.ask-timeout", Millis), Millis)
  }

  object gameEngine {

    val maxPlayers: Int = yokohama getInt "gameEngine.max-players"

    implicit val askTimeout: Timeout =
      Duration(yokohama.getDuration("gameEngine.ask-timeout", Millis), Millis)

    val startGameInterval: FiniteDuration =
      Duration(yokohama.getDuration("gameEngine.start-interval", Millis), Millis)
  }

  object game {
    val betTimeout: FiniteDuration =
      Duration(yokohama.getDuration("game.bet-timeout", Millis), Millis)
  }

  private val yokohama = system.settings.config getConfig "yokohama"
}

trait SettingsActor {

  this: Actor =>

  val settings: Settings =
    Settings(context.system)
}
