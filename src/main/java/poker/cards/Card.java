package poker.cards;

/**
 * Created by Albert on 2015-01-09.
 */
public class Card {

    private Suit suit;
    private Rank rank;

    public Rank getRank()
    {
        return rank;
    }

    public Suit getSuit()
    {
        return suit;
    }

    @Override
    public String toString() {
        String result = rank.toString() + suit.toString();
        return result;
    }

    public Card(String card) {

        if (card.indexOf(Suit.Clubs.toString()) != -1) {
            suit = Suit.Clubs;
        } else if (card.indexOf(Suit.Diamonds.toString()) != -1) {
            suit = Suit.Diamonds;
        } else if (card.indexOf(Suit.Hearts.toString()) != -1) {
            suit = Suit.Hearts;
        } else if (card.indexOf(Suit.Spades.toString()) != -1) {
            suit = Suit.Spades;
        }
        else
        {
            System.out.println("Invalid Suit");
        }

        if(card.indexOf(Rank.Ace.toString()) != -1)
        {
            rank = Rank.Ace;
        }
        else if(card.indexOf(Rank.TWO.toString()) != -1)
        {
            rank = Rank.TWO;
        }
        else if(card.indexOf(Rank.THREE.toString()) != -1)
        {
            rank = Rank.THREE;
        }
        else if(card.indexOf(Rank.FOUR.toString()) != -1)
        {
            rank = Rank.FOUR;
        }
        else if(card.indexOf(Rank.FIVE.toString()) != -1)
        {
            rank = Rank.FIVE;
        }
        else if(card.indexOf(Rank.SIX.toString()) != -1)
        {
            rank = Rank.SIX;
        }
        else if(card.indexOf(Rank.SEVEN.toString()) != -1)
        {
            rank = Rank.SEVEN;
        }
        else if(card.indexOf(Rank.EIGHT.toString()) != -1)
        {
            rank = Rank.EIGHT;
        }
        else if(card.indexOf(Rank.NINE.toString()) != -1)
        {
            rank = Rank.NINE;
        }
        else if(card.indexOf(Rank.TEN.toString()) != -1)
        {
            rank = Rank.TEN;
        }
        else if(card.indexOf(Rank.JACK.toString()) != -1)
        {
            rank = Rank.JACK;
        }
        else if(card.indexOf(Rank.QUEEN.toString()) != -1)
        {
            rank = Rank.QUEEN;
        }
        else if(card.indexOf(Rank.KING.toString()) != -1)
        {
            rank = Rank.KING;
        }
        else
        {
            System.out.println("Invalid Rank");
        }

    }
}
