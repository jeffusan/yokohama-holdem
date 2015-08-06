package yokohama.holdem

import yokohama.holdem.types._
import yokohama.holdem.Cards.Card

trait GameToPlayerMessages {
  /* Game management messages */
  case class StartGame(status: GameInfo)
  case class EndGame(summary: GameInfo)
  // send types.StatusInfo when receiving PlayerToGameMessages.StatusRequest

  /* Generic messages per round */
  case class StartRound(status: RoundInfo)
  case class StopRound(summary: RoundInfo)

  /* Round action messages */
  case class Deal(hand: (Card, Card), status: GameInfo)
  case class NextMove(minBet: Double, maxBet: Double, s: RoundInfo, timeout: Int, retriesLeft: Int)
  case class UpdatePurse(amount: Double)
  // send types.CommunityCards to signify dealer giving the next turn
}

object GameToPlayerMessages extends GameToPlayerMessages

trait PlayerToGameMessages {
  /* Game management messages */
  case object StatusRequest
  case object OK
  case object LeaveGame

  /* Round action messages */
  case class  Bet(amount: Double)
  case object Call
  case object AllIn
  case object Fold
}
object PlayerToGameMessages extends PlayerToGameMessages

