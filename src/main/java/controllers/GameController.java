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
import services.ActiveGames.ActiveGame;
import services.ActiveGamesService;
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
    private ActiveGamesService activeGamesService;

    @Inject
    private UserProvider userProvider;

    @Inject
    private RoundProvider roundProvider;

    @Inject
    private GameProvider gameProvider;

    @FilterWith(SecureFilter.class)
    public Result game(Context context) {
        Result result = Results.html();

        //TODO get round corresponding to this game
        int gameNum = Integer.parseInt(context.getParameter("gameNum"));

        //Create Round
        Integer numPlayers = Integer.parseInt(context.getParameter("numPlayers"));
        System.out.println(numPlayers);
        Hand hands[] = pokerService.generateHands(numPlayers);

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
    public Result selectGametype(Context context) {
        Result result = Results.html();
        return result;
    }

    @FilterWith(SecureFilter.class)
    public Result multiplayer(Context context) {
        Result result = Results.html();

        List<ActiveGame> games = activeGamesService.getActiveGames();

        String username = context.getSession().get("username");

        result.render("games", games);
        result.render("user", username);
        return result;
    }

    @FilterWith(SecureFilter.class)
    public Result newMultiplayerGame(Context context)
    {
        Result result = Results.html();
        String temp = context.getParameter("user");
        Round round = activeGamesService.getHostedRound(temp);
        if(round == null) {
            String winner = "NA";
            int winningScore = -1;
            int winnerNum = -1;
            round = new Round(new Date(), winner, winnerNum);
            activeGamesService.addActiveGame(round);

            //Add host as a player
            User user = userProvider.findUserByName(temp).get();
            activeGamesService.addUserToGame(round, user);
        }

        List<String> players = activeGamesService.getGameUsernames(round);

        result.render("gameNum", activeGamesService.getRoundIndex(round));
        result.render("players", players);

        return result;
    }

    @FilterWith(SecureFilter.class)
    public Result joinGame(Context context) {
        Result result = Results.html();

        int gameNum = Integer.parseInt(context.getParameter("gameNum"))-1;
        Round round = activeGamesService.getRoundAt(gameNum);

        String username = context.getParameter("user");
        User user = userProvider.findUserByName(username).get();
        System.out.println(activeGamesService.addUserToGame(round, user) + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        List<String> players = activeGamesService.getGameUsernames(round);
        result.render("players", players);

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
