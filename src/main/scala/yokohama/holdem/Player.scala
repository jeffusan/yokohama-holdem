package yokohama.holdem

import akka.actor.{ Actor, Props, ActorRef, ActorLogging }
import yokohama.holdem.Game._
import yokohama.holdem.Cards._

object Player {

  sealed trait Bet
  case class Call(amount: Int) extends Bet
  case object Check extends Bet
  case object Fold extends Bet

}

class Player extends Actor with ActorLogging {

  import Player._

  override def receive: Receive = {

    case MakeMove => {

      log info "Receive MakeMove Message"
      sender ! evaluate()
    }
  }

  private def evaluate(): Bet = {
    Call(2)
  }
}
