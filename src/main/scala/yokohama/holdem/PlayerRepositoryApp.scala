package yokohama.holdem

import akka.actor.{ ActorRef, ActorSystem, Props }
import scala.annotation.tailrec
import scala.io.StdIn
import akka.pattern.ask
import yokohama.holdem.PlayerRepository._


object PlayerRepositoryApp extends Base with Terminal {

  override protected def initialize(system: ActorSystem, settings: Settings): ActorRef =
    system.actorOf(PlayerRepository.props, PlayerRepository.name)

  @tailrec
  override protected def commandLoop(system: ActorSystem, settings: Settings, topLevel: ActorRef): Unit = {

    def register(name: String, props: Props, count: Int): Unit = {

      def doRegister(name: String, props: Props) = {

        import settings.app.askTimeout
        import scala.concurrent.ExecutionContext.Implicits.global

        val registerPlayerResponse = topLevel ? RegisterPlayer(name, props)

        registerPlayerResponse onSuccess {

          case PlayerRegistered(name) => system.log.warning("Player Registered {}", name)
        }

        registerPlayerResponse onFailure {

          case _ => system.log.error("Unable to register player {}", name)
        }
      }

      if(count > 1) {

        for (n <- 1 to count) {

          doRegister(s"$name-$n", props)
        }
      } else {
        doRegister(name, props)
      }
    }

    Command(StdIn.readLine().split(" ")) match {

      case Command.Unknown(command, message) =>
        system.log.warning("Command Unknown {} ({})!", command, message)
        commandLoop(system, settings, topLevel)

      case Command.Register(name, props, count) =>
        system.log.warning("registering...")
        register(name, props, count)
        commandLoop(system, settings, topLevel)

      case Command.Shutdown =>
        system.log.warning("shutting down...")
        system.shutdown()

      case _ =>
        system.log.warning("hail mary")
        commandLoop(system, settings, topLevel)
    }
  }
}
