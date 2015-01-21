package poker.cards;

/**
 * Created by Albert on 2015-01-09.
 */
public enum Rank {
    Ace ("A"),
    TWO ("2"),
    THREE ("3"),
    FOUR ("4"),
    FIVE ("5"),
    SIX ("6"),
    SEVEN ("7"),
    EIGHT ("8"),
    NINE ("9"),
    TEN ("10"),
    JACK ("J"),
    QUEEN ("Q"),
    KING ("K");

    private final String symbol;

    private Rank(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
