package yokohama.holdem

import org.scalatest._
import yokohama.holdem.Cards._

class CardSpec extends FlatSpec with Matchers {

  "A Hand" should "evaluate to a Value" in {

    val hand = Set(D1, S1, H4, SQ)
    val response = CardEvaluation().evaluate(hand)
    assert(response == HighCard(12))
  }

  "A Full Hand" should "never contain more than 7 cards" in {
    val hand = Set(D1, S1, H4, SQ, C1, H10, HA, CA)
    try {
      val c = CardEvaluation()
      c.evaluate(hand)
      fail()
    } catch {
      case _: IllegalArgumentException => // Expected, so continue
    }
  }

  "Four matching values" should "evaluate to FourOfKind" in {
    val hand = Set(D1, H1, S1, C1, CK)
    val response = CardEvaluation().evaluate(hand)
    assert(response == FourOfKind)
  }

  "Three and two values" should "evaluate to a FullHouse" in {
    val hand = Set(D1, H1, S1, CK, DK)
    val response = CardEvaluation().evaluate(hand)
    assert(response == FullHouse)
  }

  "Three of a kind" should "evaluate to ThreeOfKind" in {
    val hand = Set(D1, H1, S1, CK, DQ)
    val response = CardEvaluation().evaluate(hand)
    assert(response == ThreeOfKind)
  }

  "Two pair" should "evaluate to TwoPair" in {
    val hand = Set(D1, H1, S10, CK, DK)
    val response = CardEvaluation().evaluate(hand)
    assert(response == TwoPair)
  }

}
