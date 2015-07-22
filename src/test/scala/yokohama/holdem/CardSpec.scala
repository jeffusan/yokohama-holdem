package yokohama.holdem

import org.scalatest._
import yokohama.holdem.Cards._

class CardSpec extends FlatSpec with Matchers {

  "A Hand" should "evaluate to a Value" in {

    val hand = Set(D1, S1, H4, SQ)
    val response = CardEvaluation.evaluate(hand)
    assert(response == HighCard(1))
  }
}
