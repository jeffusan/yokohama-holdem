package yokohama.holdem

import org.scalatest._
import yokohama.holdem.Cards._

class CardSpec extends FlatSpec with MustMatchers {

  "A Hand" should "evaluate to a Value" in {

    val hand = Set(DA, SA, H4, SQ)
    val response = CardEvaluation().evaluate(hand)
    response mustBe HighCard(14)
  }

  it should "never be empty" in {
    val hand = Set[Card]()
    // FIXME: should throw IllegalArgumentException?
    an[ArrayIndexOutOfBoundsException] must be thrownBy CardEvaluation().evaluate(hand)
  }

  "A Full Hand" should "never contain more than 7 cards" in {
    val hand = Set(DA, SA, H4, SQ, CA, H10, HA, C2)
    an[IllegalArgumentException] must be thrownBy CardEvaluation().evaluate(hand)
  }

  "Straight flush cards of ace to ten matching values" should "evaluate RoyalStraightFlush" in pendingUntilFixed {
    val hand = Set(SA, SK, SQ, SJ, S10)
    val response = CardEvaluation().evaluate(hand)
    response mustBe RoyalStraightFlush
  }

  "Cards of straight and same suit matching values" should "evaluate StraightFlush" in pendingUntilFixed {
    val hand = Set(S6, S5, S4, S3, S2)
    val response = CardEvaluation().evaluate(hand)
    response mustBe StraightFlush
  }

  "Four matching values" should "evaluate to FourOfKind" in {
    val hand = Set(DA, HA, SA, CA, CK)
    val response = CardEvaluation().evaluate(hand)
    response mustBe FourOfKind
  }

  "Three and two values" should "evaluate to a FullHouse" in {
    val hand = Set(DA, HA, SA, CK, DK)
    val response = CardEvaluation().evaluate(hand)
    response mustBe FullHouse
  }

  "Cards of same suit matching values" should "evaluate Flush" in pendingUntilFixed {
    val hand = Set(S7, S5, S4, S3, S2)
    val response = CardEvaluation().evaluate(hand)
    response mustBe Flush
  }

  "Cards of straight matching values" should "evaluate Straight" in pendingUntilFixed {
    val hand = Set(S6, D5, H4, C3, S2)
    val response = CardEvaluation().evaluate(hand)
    response mustBe Straight
  }

  "Three of a kind" should "evaluate to ThreeOfKind" in {
    val hand = Set(DA, HA, SA, CK, DQ)
    val response = CardEvaluation().evaluate(hand)
    response mustBe ThreeOfKind
  }

  "Two pair" should "evaluate to TwoPair" in {
    val hand = Set(DA, HA, S10, CK, DK)
    val response = CardEvaluation().evaluate(hand)
    response mustBe TwoPair
  }

  "One pair" should "evaluate to Pair" in {
    val hand = Set(DA, HA, S10, CK, DQ)
    val response = CardEvaluation().evaluate(hand)
    response mustBe Pair
  }

  "No pair" should "evaluate to HighCard" in pendingUntilFixed {
    val hand = Set(D9, H3, S10, CK, DQ)
    val response = CardEvaluation().evaluate(hand)
    response mustBe HighCard(13)
  }

}
