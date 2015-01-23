package services;

import com.google.inject.Singleton;
import poker.cards.Hand;
import poker.evaluators.HandEvaluator;
import poker.generator.Generator;

/**
 * Created by Albert on 2015-01-12.
 */
@Singleton
public class PokerService implements IPokerService {

    //evaluateHand
    public String getHandString(Hand hand)
    {
        return HandEvaluator.getHandString(hand);
    }

    @Override
    public Hand generateHand() {
        return Generator.generateHand();
    }

    @Override
    public Hand[] generateHands(int numHands){
        return Generator.generateHands(numHands);
    }
}
