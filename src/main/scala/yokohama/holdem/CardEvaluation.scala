package yokohama.holdem

import yokohama.holdem.Cards._
import scala.util.Sorting.quickSort
import org.slf4j.LoggerFactory

case class CardEvaluation() {

  val logger = LoggerFactory.getLogger("yokohama.holdem.CardEvaluator");

  def determineHighCard(hand: Set[Card]): Value = {
    val h = hand.toArray
    quickSort(h)
    HighCard(valueOf(h(h.size-1).rank))
  }

  def assessFullHand(hand: FullHand): Value = {



    def checkTheRest(hand: FullHand): Value = {
      HighCard(1)
    }

    val h = hand.toArray
    quickSort(h)
    // map to histogram
    val m = h.toList.groupBy { a => valueOf(a.rank) }.mapValues { b => b.size }
    val g = m.values.toArray
    quickSort(g)
    val x = g.toList.reverse

    x match {
      case x :: xs if x == 4 && xs == List(1) => FourOfKind
      case x :: xs if x == 3 && xs == List(2)  => FullHouse
      case x :: xs if x == 3 && xs == List(1, 1) => ThreeOfKind
      case x :: xs if x == 2 && xs == List(2, 1) => TwoPair
      case x :: xs if x == 2 && xs == List(1, 1, 1) => Pair
      case _ => checkTheRest(h.toSet)
    }

  }

  def evaluate(hand: FullHand): Value = hand.toList.size match {
    case l if l > 7 => throw new IllegalArgumentException("FullHand cannot be more than 7 cards")
    case l if l >= 5 && l < 7 => assessFullHand(hand)
    case l if l < 5 => determineHighCard(hand)
  }
}
