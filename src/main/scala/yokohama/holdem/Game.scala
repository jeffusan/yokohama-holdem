package yokohama.holdem

import akka.actor.{ActorRef, Props, Actor, ActorLogging}
import akka.util.Timeout
import akka.pattern.ask
import scala.util.{ Success, Failure }
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import Cards._
import Player._

/**
  * Presently, this is just a template for a game.
  */
object Game {

  val deck = new Deck()

  sealed trait Deal {
    val cardCount = 0
  }

  case object MakeMove extends Deal {
    override val cardCount = 2
  }

  def props(): Props =
    Props(new Game())
}

/**
  * Definition of the Game
  */
class Game() extends Actor with ActorLogging {

  import Game._

  def receive = {
    case _ =>

  }

}
