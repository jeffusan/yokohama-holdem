package yokohama.holdem

import org.scalatest._
import yokohama.holdem.Cards._

class CardSpec extends FlatSpec with MustMatchers {

  "A Hand" should "evaluate to a Value" in {

    val hand = Set(D1, S1, H4, SQ)
    val response = CardEvaluation().evaluate(hand)
    response mustBe HighCard(12)
  }

  it should "never be empty" in {
    val hand = Set[Card]()
    // FIXME: should throw IllegalArgumentException?
    an[ArrayIndexOutOfBoundsException] must be thrownBy CardEvaluation().evaluate(hand)
  }

  "A Full Hand" should "never contain more than 7 cards" in {
    val hand = Set(D1, S1, H4, SQ, C1, H10, HA, CA)
    an[IllegalArgumentException] must be thrownBy CardEvaluation().evaluate(hand)
  }

  "Four matching values" should "evaluate to FourOfKind" in {
    val hand = Set(D1, H1, S1, C1, CK)
    val response = CardEvaluation().evaluate(hand)
    response mustBe FourOfKind
  }

  "Three and two values" should "evaluate to a FullHouse" in {
    val hand = Set(D1, H1, S1, CK, DK)
    val response = CardEvaluation().evaluate(hand)
    response mustBe FullHouse
  }

  "Three of a kind" should "evaluate to ThreeOfKind" in {
    val hand = Set(D1, H1, S1, CK, DQ)
    val response = CardEvaluation().evaluate(hand)
    response mustBe ThreeOfKind
  }

  "Two pair" should "evaluate to TwoPair" in {
    val hand = Set(D1, H1, S10, CK, DK)
    val response = CardEvaluation().evaluate(hand)
    response mustBe TwoPair
  }

  "One pair" should "evaluate to Pair" in {
    val hand = Set(D1, H1, S10, CK, DQ)
    val response = CardEvaluation().evaluate(hand)
    response mustBe Pair
  }

}
