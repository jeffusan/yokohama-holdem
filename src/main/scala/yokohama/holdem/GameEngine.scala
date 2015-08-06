package yokohama.holdem

import akka.actor.{ Actor, ActorLogging, ActorRef, FSM, Props, Terminated }
import akka.contrib.pattern.ClusterSingletonProxy
import akka.routing.FromConfig
import akka.util.Timeout
import scala.concurrent.duration.FiniteDuration

/**
  * A game engine organizes, creates, and manages games.
  */
object GameEngine {

  sealed trait State

  object State {

    case object Pausing extends State
    case object Running extends State

  }

  case class GameData( game: Option[ActorRef] = None )

  val name: String = "game-engine"

  def props( askTimeout: Timeout, startGameInterval: FiniteDuration, maxPlayers: Int): Props = Props(new GameEngine(askTimeout, startGameInterval, maxPlayers))

}

class GameEngine(askTimeout: Timeout, startGameInterval: FiniteDuration, maxPlayers: Int) extends Actor with FSM[GameEngine.State, GameEngine.GameData] with ActorLogging with SettingsActor {

  import GameEngine._

  private val playerRepository =
    context.actorOf(
      ClusterSingletonProxy.props(s"/user/singleton/${PlayerRepository.name}", Some(PlayerRepository.name)), PlayerRepository.name)

  startWith(State.Pausing, GameData())

  when(State.Pausing, startGameInterval) {

    case Event(StateTimeout, data) => goto(State.Running) using data.copy(game = Some(startGame()))
  }

  when(State.Running) {
    case Event(Terminated(_), data) => goto(State.Pausing) using data.copy(game = None)
  }

  onTransition {
    case _ -> State.Pausing => log.debug("Transitioning to pausing gamestate")
    case _ -> State.Running => log.debug("Transitioning to running gamestate")
  }

  initialize()

  private def startGame(): ActorRef = {
    log.info("Starting a new poker game")
    context.watch(createGame())
  }

  protected def createGame(): ActorRef = {
    context.actorOf(Game.props(), "name")
  }
    

}

