package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import database.*;
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

    @Inject
    UserProvider userProvider;

    @Inject
    RoundProvider roundProvider;

    @Inject
    GameProvider gameProvider;

    @FilterWith(SecureFilter.class)
    public Result game(Context context) {
        Result result = Results.html();

        //Create Round
        Hand hands[] = pokerService.generateHands();

        //store in db
        Round round = new Round();
        roundProvider.persist(round);

        List<Card> cards = new LinkedList<>();
        List<String> evaluate = new LinkedList<>();

        for(int h = 0; h < hands.length; h++) {
            for (Card card : hands[h].getCards()) {
                cards.add(card);
            }

            String evaluateString = HandEvaluator.getHandString(hands[h]);
            evaluate.add(evaluateString);

            String username = context.getSession().get("username");

            User user = userProvider.findUserByName(username).get();

            Game game = new Game(hands[h].toString(), evaluateString, user, round);
            gameProvider.persist(game);
        }

        List<String> users = new LinkedList<>();

        //TODO Actual Users
        for (int i = 0; i < 6; i++) {
            users.add("user" + (i + 1));
        }

        result.render("users", users);
        result.render("cards", cards);
        result.render("evaluate", evaluate);

        return result;
    }

    @FilterWith(SecureFilter.class)
    public Result selectUsers(Context context) {
        Result result = Results.html();

        List<User> users = userProvider.getAllUsers();
        List<String> usernames = new LinkedList<>();

        for (User user : users)
            usernames.add(user.getUsername());


        String superUser = context.getSession().get("username");

        result.render("superUser", superUser);
        result.render("usernames", usernames);

        return result;
    }
}
