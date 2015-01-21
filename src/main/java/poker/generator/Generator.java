package poker.generator;

import poker.cards.Card;
import poker.cards.Hand;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Albert on 2015-01-12.
 */
public class Generator {

    private static List<Card> generateDeck()
    {
        List<Card> deck = new LinkedList<>();

        String[] suits = {"S", "H", "C", "D"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for(String suit : suits)
        {
            for(String rank : ranks)
            {
                deck.add(new Card(rank + suit));
            }
        }

        Collections.shuffle(deck);

        return deck;
    }

    public static Hand generateHand()
    {
        List<Card> deck = generateDeck();

        Hand hand = new Hand(deck.get(0), deck.get(1), deck.get(2), deck.get(3),deck.get(4));

        return hand;
    }

    public static Hand[] generateHands()
    {
        List<Card> deck = generateDeck();

        Hand hands[] = new Hand[6];

        int i = 0;

        for(int h = 0; h < 6; h++) {
            hands[h] = new Hand(deck.get(i++), deck.get(i++), deck.get(i++), deck.get(i++),deck.get(i++));
        }

        return hands;
    }
}
