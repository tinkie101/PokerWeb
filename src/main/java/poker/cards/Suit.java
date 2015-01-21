package poker.cards;

/**
 * Created by Albert on 2015-01-09.
 */
public enum Suit {
    Spades("S"),
    Clubs ("C"),
    Diamonds ("D"),
    Hearts("H");

    private final String symbol;

    private Suit(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
