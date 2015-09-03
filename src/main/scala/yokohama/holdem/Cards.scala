package yokohama.holdem

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

  case class Card(suit: Suit, rank: Rank) extends Ordered[Card] {

    def compare(that: Card): Int = {
      valueOf(this.rank) - valueOf(that.rank)
    }
  }

  // Sets guarantee unique results
  type BestHand = Set[Card]
  type FullHand = Set[Card]

  val D2 = Card(Diamonds, Numeric(2))
  val D3 = Card(Diamonds, Numeric(3))
  val D4 = Card(Diamonds, Numeric(4))
  val D5 = Card(Diamonds, Numeric(5))
  val D6 = Card(Diamonds, Numeric(6))
  val D7 = Card(Diamonds, Numeric(7))
  val D8 = Card(Diamonds, Numeric(8))
  val D9 = Card(Diamonds, Numeric(9))
  val D10 = Card(Diamonds, Numeric(10))
  val DJ = Card(Diamonds, Jack)
  val DQ = Card(Diamonds, Queen)
  val DK = Card(Diamonds, King)
  val DA = Card(Diamonds, Ace)


  val S2 = Card(Spades, Numeric(2))
  val S3 = Card(Spades, Numeric(3))
  val S4 = Card(Spades, Numeric(4))
  val S5 = Card(Spades, Numeric(5))
  val S6 = Card(Spades, Numeric(6))
  val S7 = Card(Spades, Numeric(7))
  val S8 = Card(Spades, Numeric(8))
  val S9 = Card(Spades, Numeric(9))
  val S10 = Card(Spades, Numeric(10))
  val SJ = Card(Spades, Jack)
  val SQ = Card(Spades, Queen)
  val SK = Card(Spades, King)
  val SA = Card(Spades, Ace)


  val H2 = Card(Hearts, Numeric(2))
  val H3 = Card(Hearts, Numeric(3))
  val H4 = Card(Hearts, Numeric(4))
  val H5 = Card(Hearts, Numeric(5))
  val H6 = Card(Hearts, Numeric(6))
  val H7 = Card(Hearts, Numeric(7))
  val H8 = Card(Hearts, Numeric(8))
  val H9 = Card(Hearts, Numeric(9))
  val H10 = Card(Hearts, Numeric(10))
  val HJ = Card(Hearts, Jack)
  val HQ = Card(Hearts, Queen)
  val HK = Card(Hearts, King)
  val HA = Card(Hearts, Ace)


  val C2 = Card(Clubs, Numeric(2))
  val C3 = Card(Clubs, Numeric(3))
  val C4 = Card(Clubs, Numeric(4))
  val C5 = Card(Clubs, Numeric(5))
  val C6 = Card(Clubs, Numeric(6))
  val C7 = Card(Clubs, Numeric(7))
  val C8 = Card(Clubs, Numeric(8))
  val C9 = Card(Clubs, Numeric(9))
  val C10 = Card(Clubs, Numeric(10))
  val CJ = Card(Clubs, Jack)
  val CQ = Card(Clubs, Queen)
  val CK = Card(Clubs, King)
  val CA = Card(Clubs, Ace)

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
      D2, D3, D4, D5, D6, D7, D8, D9, D10, DJ, DQ, DK, DA, S2,
      S3, S4, S5, S6, S7, S8, S9, S10, SJ, SQ, SK, SA, H2, H3, H4,
      H5, H6, H7, H8, H9, H10, HJ, HQ, HK, HA, C2, C3, C4, C5, C6,
      C7, C8, C9, C10, CJ, CQ, CK, CA))

    def slice(start: Int, end: Int): List[Card] = {
      c.slice(start, end)
    }
  }

}
