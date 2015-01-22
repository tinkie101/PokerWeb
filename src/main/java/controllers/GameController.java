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

import java.util.*;

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
        Round round = new Round(new Date());
        roundProvider.persist(round);

        List<Card> cards = new LinkedList<>();
        List<String> evaluate = new LinkedList<>();
        List<String> users = new LinkedList<>();

        String username = context.getSession().get("username");

        for(int h = 0; h < hands.length; h++) {
            for (Card card : hands[h].getCards()) {
                cards.add(card);
            }

            String evaluateString = HandEvaluator.getHandString(hands[h]);
            evaluate.add(evaluateString);

            String temp = context.getParameter("user"+(h+1));
            users.add(temp);
            User user = userProvider.findUserByName(temp).get();

            Game game = new Game(hands[h].toString(), evaluateString, user, round);
            gameProvider.persist(game);
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

    @FilterWith(SecureFilter.class)
    public Result history(Context context) {
        Result result = Results.html();

        String username = context.getSession().get("username");
        List<Game> games = gameProvider.findGamesByUsername(username);
        List<History> histories = new LinkedList<>();

        for(Game game : games)
        {
            Optional<Round> round = roundProvider.findRoundByID(game.getRoundID());

            if(round.isPresent())
                histories.add(new History(game.getHand(), game.getEvaluate(), round.get().getDate()));
        }

        Collections.sort(histories, new Comparator<History>() {
            @Override
            public int compare(History o1, History o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        result.render("user", username);
        result.render("histories", histories);

        return result;
    }

}
