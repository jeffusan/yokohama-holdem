package yokohama.holdout

import scala.util.Random

object Cards {

  sealed trait Color
  case object Red extends Color
  case object Black extends Color

  sealed trait Suit
  case object Clubs extends Suit
  case object Diamonds extends Suit
  case object Hearts extends Suit
  case object Spades extends Suit

  sealed trait Rank
  case object Jack extends Rank
  case object Queen extends Rank
  case object King extends Rank
  case object Ace extends Rank
  case class Numeric(value: Int) extends Rank

  sealed trait Value
  case object RoyalStraightFlush extends Value
  case object StraightFlush extends Value
  case object FourOfKind extends Value
  case object FullHouse extends Value
  case object Flush extends Value
  case object Straight extends Value
  case object ThreeOfKind extends Value
  case object TwoPair extends Value
  case object Pair extends Value
  case class HighCard(value: Int) extends Value

  type Card = (Suit, Rank)
  type Hand = Seq[Card]

  val D1 = (Diamonds, Numeric(1))
  val D2 = (Diamonds, Numeric(2))
  val D3 = (Diamonds, Numeric(3))
  val D4 = (Diamonds, Numeric(4))
  val D5 = (Diamonds, Numeric(5))
  val D6 = (Diamonds, Numeric(6))
  val D7 = (Diamonds, Numeric(7))
  val D8 = (Diamonds, Numeric(8))
  val D9 = (Diamonds, Numeric(9))
  val D10 = (Diamonds, Numeric(10))
  val DJ = (Diamonds, Jack)
  val DQ = (Diamonds, Queen)
  val DK = (Diamonds, King)
  val DA = (Diamonds, Ace)

  val S1 = (Spades, Numeric(1))
  val S2 = (Spades, Numeric(2))
  val S3 = (Spades, Numeric(3))
  val S4 = (Spades, Numeric(4))
  val S5 = (Spades, Numeric(5))
  val S6 = (Spades, Numeric(6))
  val S7 = (Spades, Numeric(7))
  val S8 = (Spades, Numeric(8))
  val S9 = (Spades, Numeric(9))
  val S10 = (Spades, Numeric(10))
  val SJ = (Spades, Jack)
  val SQ = (Spades, Queen)
  val SK = (Spades, King)
  val SA = (Spades, Ace)

  val H1 = (Hearts, Numeric(1))
  val H2 = (Hearts, Numeric(2))
  val H3 = (Hearts, Numeric(3))
  val H4 = (Hearts, Numeric(4))
  val H5 = (Hearts, Numeric(5))
  val H6 = (Hearts, Numeric(6))
  val H7 = (Hearts, Numeric(7))
  val H8 = (Hearts, Numeric(8))
  val H9 = (Hearts, Numeric(9))
  val H10 = (Hearts, Numeric(10))
  val HJ = (Hearts, Jack)
  val HQ = (Hearts, Queen)
  val HK = (Hearts, King)
  val HA = (Hearts, Ace)

  val C1 = (Clubs, Numeric(1))
  val C2 = (Clubs, Numeric(2))
  val C3 = (Clubs, Numeric(3))
  val C4 = (Clubs, Numeric(4))
  val C5 = (Clubs, Numeric(5))
  val C6 = (Clubs, Numeric(6))
  val C7 = (Clubs, Numeric(7))
  val C8 = (Clubs, Numeric(8))
  val C9 = (Clubs, Numeric(9))
  val C10 = (Clubs, Numeric(10))
  val CJ = (Clubs, Jack)
  val CQ = (Clubs, Queen)
  val CK = (Clubs, King)
  val CA = (Clubs, Ace)

  def valueOf(rank: Rank): Int = rank match {
    case Ace        => 14
    case King       => 13
    case Queen      => 12
    case Jack       => 11
    case Numeric(n) => n
  }

  def colorOf(suit: Suit): Color = suit match {
    case Clubs  => Black
    case Spades => Black
    case _      => Red
  }

  case class Deck() {
    val c = Random.shuffle(List(
      D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, DJ, DQ, DK, DA, S1, S2,
      S3, S4, S5, S6, S7, S8, S9, S10, SJ, SQ, SK, SA, H1, H2, H3, H4,
      H5, H6, H7, H8, H9, H10, HJ, HQ, HK, HA, C1, C2, C3, C4, C5, C6,
      C7, C8, C9, C10, CJ, CQ, CK, CA))

    def slice(start: Int, end: Int): List[Card] = {
      c.slice(start, end)
    }
  }

}
