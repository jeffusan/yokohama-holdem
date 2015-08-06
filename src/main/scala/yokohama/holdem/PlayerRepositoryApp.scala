package yokohama.holdem

import akka.actor.{ ActorRef, ActorSystem, Props, PoisonPill }
import akka.contrib.pattern.{ ClusterSingletonManager, ClusterSingletonProxy }
import scala.annotation.tailrec
import scala.io.StdIn
import akka.pattern.ask

object PlayerRepositoryApp extends Base with Terminal {

  override protected def initialize(system: ActorSystem, settings: Settings): ActorRef = {
    val singletonName = "singleton"
    val role = Some(PlayerRepository.name)

    system.actorOf(
      ClusterSingletonManager.props(
        PlayerRepository.props,
        PlayerRepository.name,
        PoisonPill,
        role),
      singletonName
    )
    system.actorOf(
      ClusterSingletonProxy.props(s"/user/$singletonName/${PlayerRepository.name}", role),
      PlayerRepository.name
    )
  }

  @tailrec
  override protected def commandLoop(system: ActorSystem, settings: Settings, topLevel: ActorRef): Unit = {

    def register(name: String, props: Props, count: Int): Unit = {

      def doRegister(name: String, props: Props) = {

        import settings.app.askTimeout
        import scala.concurrent.ExecutionContext.Implicits.global

        val registerPlayerResponse = topLevel ? PlayerRepository.RegisterPlayer(name, props)

        registerPlayerResponse onSuccess {

          case PlayerRepository.PlayerRegistered(name) => system.log.warning("Player Registered {}", name)
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
