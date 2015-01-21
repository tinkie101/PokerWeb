package poker.cards;

import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Albert on 2015-01-09.
 */
public class Hand
{

    private LinkedList<Card> cards;

    public Hand(String card1, String card2, String card3, String card4, String card5)
    {
        cards = new LinkedList<>();

        cards.add(new Card(card1));
        cards.add(new Card(card2));
        cards.add(new Card(card3));
        cards.add(new Card(card4));
        cards.add(new Card(card5));
    }

    public Hand(Card ... inputCards)
    {
        cards = new LinkedList<>();

        for(Card card : inputCards)
        {
            cards.add(card);
        }
    }

    public LinkedList<Card> getCards()
    {
        return cards;
    }

    @Override
    public String toString()
    {
        StringJoiner joiner = new StringJoiner(",", "", "");

        for(int i = 0; i < cards.size(); i++)
        {
            joiner.add(cards.get(i).toString());
        }

        return joiner.toString();
    }
}
