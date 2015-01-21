package poker.evaluators;

import junit.framework.Assert;
import poker.cards.Hand;
import org.junit.Test;

import static org.junit.Assert.*;

public class HandEvaluatorTest {

    //Test isStraitFlush()
    @Test
    public void shouldMatchStraitFlush()
    {
        Hand hand1 = new Hand("3S","4S","5S","6S","7S");
        Hand hand2 = new Hand("4S","6S","5S", "3S","7S");

        assertTrue(HandEvaluator.isStraitFlush(hand1));
        assertTrue(HandEvaluator.isStraitFlush(hand2));
    }

    @Test
    public void shouldNotMatchStraitFlush()
    {
        Hand hand = new Hand("3S", "5C", "8H", "9H", "KD");

        assertFalse(HandEvaluator.isStraitFlush(hand));
    }


    //Test isFourOfAKind()
    @Test
    public void shouldMatchFourOfAKind()
    {
        Hand hand1 = new Hand("3S","4H","4S","4C","4D");
        Hand hand2 = new Hand("KH","KS","3S","KC","KD");

        assertTrue(HandEvaluator.isFourOfAKind(hand1));
        assertTrue(HandEvaluator.isFourOfAKind(hand2));
    }

    @Test
    public void shouldNotMatchFourOfAKind()
    {
        Hand hand = new Hand("3S", "5C", "8H", "9H", "KD");
        Hand hand2 = new Hand("3S","4H","4S","4C","3D");

        assertFalse(HandEvaluator.isFourOfAKind(hand));
        assertFalse(HandEvaluator.isFourOfAKind(hand2));
    }

    //test isFullHouse
    @Test
    public void shouldMatchFullHouse()
    {
        Hand hand1 = new Hand("3S","4H","4S","4C","3D");
        Hand hand2 = new Hand("KH","KS","3S","3C","3D");

        assertTrue(HandEvaluator.isFullHouse(hand1));
        assertTrue(HandEvaluator.isFullHouse(hand2));
    }

    @Test
    public void shouldNotMatchFullHouse()
    {
        Hand hand = new Hand("3S", "3C", "8H", "9H", "KD");
        Hand hand2 = new Hand("3S","3C","3D", "9S", "KS");
        Hand hand3 = new Hand("3S","4S","KS","6S","QS");

        assertFalse(HandEvaluator.isFullHouse(hand));
        assertFalse(HandEvaluator.isFullHouse(hand2));
        assertFalse(HandEvaluator.isFullHouse(hand3));
    }

    //test isFlush()
    @Test
    public void shouldMatchFlush()
    {
        Hand hand1 = new Hand("3S","4S","KS","7S","2S");
        Hand hand2 = new Hand("KH","QH","3H","AH","3H");

        assertTrue(HandEvaluator.isFlush(hand1));
        assertTrue(HandEvaluator.isFlush(hand2));
    }

    @Test
    public void shouldNotMatchFlush()
    {
        Hand hand1 = new Hand("3S","4S","5S","6S","7S");
        Hand hand2 = new Hand("4S","6D","5C", "3S","7H");
        Hand hand3 = new Hand("3S","3C","3D", "9S", "KS");

        assertFalse(HandEvaluator.isFlush(hand1));
        assertFalse(HandEvaluator.isFlush(hand2));
        assertFalse(HandEvaluator.isFlush(hand3));
    }

    //test isStrait()
    @Test
    public void shouldMatchStrait()
    {
        Hand hand1 = new Hand("3S","4C","5S","6H","7D");
        Hand hand2 = new Hand("KH","QH","JC","10H","9S");

        assertTrue(HandEvaluator.isStrait(hand1));
        assertTrue(HandEvaluator.isStrait(hand2));
    }

    @Test
    public void shouldNotMatchStrait()
    {
        Hand hand1 = new Hand("3S","4S","5S","6S","7S");
        Hand hand2 = new Hand("JS","6S","KS", "3S","9S");
        Hand hand3 = new Hand("3S","3C","3D", "9S", "KS");

        assertFalse(HandEvaluator.isStrait(hand1));
        assertFalse(HandEvaluator.isStrait(hand2));
        assertFalse(HandEvaluator.isStrait(hand3));
    }

    //test isThreeOfAKind()
    @Test
    public void shouldMatchThreeOfAKind()
    {
        Hand hand1 = new Hand("3S","3C","3D","6H","7D");
        Hand hand2 = new Hand("7S","4H","4S","4C","3D");

        assertTrue(HandEvaluator.isThreeOfAKind(hand1));
        assertTrue(HandEvaluator.isThreeOfAKind(hand2));
    }

    @Test
    public void shouldNotMatchThreeOfAKind()
    {
        Hand hand1 = new Hand("3S","4H","4S","4C","3D");
        Hand hand2 = new Hand("2S","6S","5S", "3C","7H");

        assertFalse(HandEvaluator.isThreeOfAKind(hand1));
        assertFalse(HandEvaluator.isThreeOfAKind(hand2));
    }

    //test isTwoPair()
    @Test
    public void shouldMatchTwoPair()
    {
        Hand hand1 = new Hand("3S","3C","AD","6H","6D");
        Hand hand2 = new Hand("KS","4H","KS","4C","3D");

        assertTrue(HandEvaluator.isTwoPair(hand1));
        assertTrue(HandEvaluator.isTwoPair(hand2));
    }

    @Test
    public void shouldNotMatchTwoPair()
    {
        Hand hand1 = new Hand("3S","3C","3D","6H","7D");
        Hand hand2 = new Hand("3S","4H","4S","4C","4D");
        Hand hand3 = new Hand("3S","4H","6S","2C","4D");
        Hand hand4 = new Hand("3S","4H","6S","2C","8D");
        Hand hand5 = new Hand("3S","3C","3D","6H","6D");

        assertFalse(HandEvaluator.isTwoPair(hand1));
        assertFalse(HandEvaluator.isTwoPair(hand2));
        assertFalse(HandEvaluator.isTwoPair(hand3));
        assertFalse(HandEvaluator.isTwoPair(hand4));
        assertFalse(HandEvaluator.isTwoPair(hand5));
    }

    //test isOnePair()
    @Test
    public void shouldMatchOnePair()
    {
        Hand hand1 = new Hand("3S","3C","AD","6H","7D");
        Hand hand2 = new Hand("KS","4H","KS","JC","3D");

        assertTrue(HandEvaluator.isOnePair(hand1));
        assertTrue(HandEvaluator.isOnePair(hand2));
    }

    @Test
    public void shouldNotMatchOnePair()
    {
        Hand hand1 = new Hand("3S","3C","3D","6H","7D");
        Hand hand2 = new Hand("3S","4H","4S","4C","4D");
        Hand hand3 = new Hand("3S","4H","6S","3C","4D");
        Hand hand4 = new Hand("3S","4H","4S","3C","4D");
        Hand hand5 = new Hand("3S","2H","6S","QC","5D");

        assertFalse(HandEvaluator.isOnePair(hand1));
        assertFalse(HandEvaluator.isOnePair(hand2));
        assertFalse(HandEvaluator.isOnePair(hand3));
        assertFalse(HandEvaluator.isOnePair(hand4));
        assertFalse(HandEvaluator.isOnePair(hand5));
    }

//    @Test
//    public void test()
//    {
//        Hand hand1 = new Hand("3S","3C","7D","6H","7D");
//
//        HandEvaluator.functionalRankMap(hand1);
//    }
}