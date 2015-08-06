package yokohama.holdem.types

import yokohama.holdem.Cards.Card

case class PlayerInfo(id: String, name: String, purse: Double)

case class CommunityCards(flop: Option[(Card, Card, Card)], turn: Option[Card], river: Option[Card])

trait StatusInfo {
  val msg: Option[String]
}

case class GameInfo(
  uuid: java.util.UUID,
  players: List[PlayerInfo], // Ordered list according to position
  roundInfo: Option[RoundInfo],
  msg: Option[String] = None) extends StatusInfo

case class RoundInfo(
  num: Int,
  dealer: PlayerInfo,
  smallBlind: PlayerInfo,
  bigBlind: PlayerInfo,
  currentlyBetting: Option[PlayerInfo],
  bets: List[PlayerInfo],
  cc: CommunityCards,
  pot: Double,
  msg: Option[String] = None) extends StatusInfo

