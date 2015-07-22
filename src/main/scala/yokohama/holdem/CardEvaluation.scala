package yokohama.holdem

import yokohama.holdem.Cards._
import scala.util.Sorting.quickSort
import org.slf4j.LoggerFactory

/**
  * Evaluating Cards:
  * 1- iterate over all cards, remove one at a time, save best hand and returning.
  * 2- once at five cards, create a 5hand type, create a histogram.
  * for each rank in the hand, count how often it appears.
  * Sort the histogram by the backward count (high to low).
  * 3- If this histogram counts are 4 and 1, then the hand is four-of-a-kind
  * 4- If the histogram counts are 3 and 2, then full-house
  * 5- if the histogram counts are 3 and 1, 1, then three-of-a-kind
  * 6- if 2,2,1 then two-pair
  * 7- if 4 ranks, then one pair
  * 8- check if it is a flush
  * 9- check for straight
  * 10- if straight + flush, then royal flush
  * 11- it is high card
  */
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
