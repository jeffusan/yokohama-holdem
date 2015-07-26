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
  * 
  */
object Game {

  val deck = new Deck()

  sealed trait Deal {
    val cardCount = 0
  }

  case object MakeMove extends Deal {
    override val cardCount = 2
  }

  def props(players: Set[String], askTimeout: Timeout): Props =
    Props(new Game(players, askTimeout))
}

/**
  * Definition of the Game
  */
class Game(players: Set[String], askTimeout: Timeout) extends Actor with ActorLogging {

  import Game._
//  implicit val askTimeout = Timeout(6.second)


  def receive = {
    case _ =>

  }

}
