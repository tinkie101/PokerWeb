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
        //TODO number of hands
        Hand hands[] = pokerService.generateHands(6);

        List<Card> cards = new LinkedList<>();
        List<String> evaluate = new LinkedList<>();
        List<String> users = new LinkedList<>();

        String username = context.getSession().get("username");

        String winner = "NA";
        int winningScore = -1;
        int winnerNum = -1;

        //store in db
        Round round = new Round(new Date(), winner, winnerNum);
        roundProvider.persist(round);

        for(int h = 0; h < hands.length; h++) {
            for (Card card : hands[h].getCards()) {
                cards.add(card);
            }

            String evaluateString = HandEvaluator.getHandString(hands[h]);
            evaluate.add(evaluateString);

            String temp = context.getParameter("user"+(h+1));
            users.add(temp);
            User user = userProvider.findUserByName(temp).get();

            int score = HandEvaluator.getHandScore(hands[h]);
            if(score > winningScore) {
                winningScore = score;
                winner = user.getUsername();
                winnerNum = h;
            }

            Game game = new Game(hands[h].toString(), evaluateString, user, round);
            gameProvider.persist(game);
        }

        round.setWinner(winner);
        round.setWinnerNum(winnerNum);
        roundProvider.merge(round);

        result.render("users", users);
        result.render("winner", winnerNum);
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
    public Result singleHistory(Context context) {
        Result result = Results.html();

        String username = context.getSession().get("username");
        List<Game> games = gameProvider.findGamesByUsername(username);
        List<SingleHistory> histories = new LinkedList<>();

        for(Game game : games)
        {
            Optional<Round> round = roundProvider.findRoundByID(game.getRoundID().getID());

            if(round.isPresent())
                histories.add(new SingleHistory(game.getHand(), game.getEvaluate(), round.get().getDate()));
        }

        Collections.sort(histories, new Comparator<SingleHistory>() {
            @Override
            public int compare(SingleHistory o1, SingleHistory o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        result.render("user", username);
        result.render("histories", histories);

        return result;
    }

}
