package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import filters.SecureFilter;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import poker.cards.Card;
import poker.cards.Hand;
import poker.evaluators.HandEvaluator;
import services.IPokerService;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Albert on 2015-01-16.
 */
@Singleton
public class GameController {

    @Inject
    private IPokerService pokerService;

    @FilterWith(SecureFilter.class)
    public Result game(Context context) {
        Result result = Results.html();

        List<String> users = new LinkedList<>();

        for(int i = 0; i < 6; i++)
        {
            users.add("user"+(i+1));
        }

        result.render("users", users);
        Hand hands[] = pokerService.generateHands();

        List<Card> cards = new LinkedList<>();
        List<String> evaluate = new LinkedList<>();

        for(int h = 0; h < hands.length; h++) {
            for (Card card : hands[h].getCards()) {
                cards.add(card);
            }

            evaluate.add(HandEvaluator.getHandString(hands[h]));
        }

        result.render("cards", cards);
        result.render("evaluate", evaluate);

        return result;

        //return Results.html();
    }

    public void setPokerService(IPokerService pokerService)
    {
        this.pokerService = pokerService;
    }
}
