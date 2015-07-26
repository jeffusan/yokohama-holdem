package yokohama.holdem

import akka.actor.{ Actor, ActorLogging, ActorPath, ActorRef, Address, Props, RootActorPath }

object PlayerRepository {
  case class RegisterPlayer(name: String, props: Props)

  case class PlayerNameTaken(name: String)

  case class PlayerRegistered(name: String)

  case class GetPlayers(count: Int)

  case class Players(players: Set[String])

  val name: String =
    "player-repository"

  def pathFor(address: Address): ActorPath =
    RootActorPath(address) / "user" / name

  def props: Props =
    Props(new PlayerRepository)

}

class PlayerRepository extends Actor with SettingsActor with ActorLogging {

  import PlayerRepository._

  private var players = Set.empty[String]

  override def receive: Receive = {
    case RegisterPlayer(name, _) if isNameTaken(name) => playerNameTaken(name: String)
    case RegisterPlayer(name, props) => registerPlayer(name, props)
    case GetPlayers(count)           => sender() ! Players(players)
  }

  private def playerNameTaken(name: String): Unit = {
    log.warning("Player name {} is taken", name)
    sender() ! PlayerNameTaken(name)
  }

  private def registerPlayer(name: String, props: Props): Unit = {
    log.info("Registering player {}", name)
    createPlayer(name, props)
    sender() ! PlayerRegistered(name)
  }

  protected def createPlayer(name: String, props: Props): ActorRef =
    context.actorOf(props, name)

  private def isNameTaken(name: String): Boolean = players contains name

}
