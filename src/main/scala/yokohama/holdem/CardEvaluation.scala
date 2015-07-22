package yokohama.holdem

import yokohama.holdem.Cards._
import scala.util.Sorting.quickSort

object CardEvaluation {

  def determineHighCard(hand: Set[Card]): Value = {
    val h = hand.toArray
    quickSort(h)
    HighCard(valueOf(h(h.size-1).rank))
  }

  def assessFullHand(hand: FullHand): Value = {
    HighCard(1)
  }

  def evaluate(hand: FullHand): Value = hand.toList.size match {
    case l if l > 7 => throw new IllegalArgumentException("FullHand cannot be more than 7 cards")
    case l if l >= 5 && l < 7 => assessFullHand(hand)
    case l if l < 5 => determineHighCard(hand)
  }
}
