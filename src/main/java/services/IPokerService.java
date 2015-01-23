package services;

import poker.cards.Hand;

/**
 * Created by Albert on 2015-01-12.
 */
public interface IPokerService {

    public String getHandString(Hand hand);
    public Hand generateHand();
    public Hand[] generateHands(int numHands);
}
