package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import database.*;
import filters.SecureFilter;
import ninja.*;
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
    private GameProviders gameProviders;

    @Inject
    Router router;

    @FilterWith(SecureFilter.class)
    public Result game(Context context) {
        Result result = Results.html();

        Round round;
        String winner = "NA";
        int winningScore = -1;
        int winnerNum = -1;
        int roundID = -1;
        try {
            roundID = Integer.parseInt(context.getParameter("roundID"));
            round = roundProvider.findRoundByID(roundID).get();
        }
        catch(Exception e)
        {
            //Round does not exist, so it is a single player game
            round = new Round(new Date(), winner, winnerNum);
            roundProvider.persist(round);
            roundID = round.getID();
        }

        if(!round.getWinner().equals("NA"))
            return Results.redirect(router.getReverseRoute(GameController.class, "selectGametype"));

        Integer numPlayers = Integer.parseInt(context.getParameter("numPlayers"));

        Hand hands[] = pokerService.generateHands(numPlayers);
        List<Card> cards = new LinkedList<>();
        List<String> evaluate = new LinkedList<>();
        List<String> users = new LinkedList<>();

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
            gameProviders.persist(game);
        }

        round.setWinner(winner);
        round.setWinnerNum(winnerNum);
        roundProvider.merge(round);

        result.render("users", users);
        result.render("winner", winnerNum);
        result.render("cards", cards);
        result.render("evaluate", evaluate);

        activeGamesService.removeActiveGame(roundID);
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
public Result activeGames(Context context) {
    Result result = Results.html();

    List<ActiveGame> games = activeGamesService.getActiveGames();

    Date oldDate = activeGamesService.getDate();
    Date newDate = activeGamesService.getDate();

    while(oldDate.compareTo(newDate) == 0) {
        try {
            Thread.sleep(100);
            newDate = activeGamesService.getDate();
            games = activeGamesService.getActiveGames();
        } catch (Exception e) {

        }
    }

    String username = context.getSession().get("username");

    result.render("games", games);
    result.render("user", username);
    return result;
}
    public Result activeJoinGames(Context context) {
        Result result = Results.html();

        int roundID = Integer.parseInt(context.getParameter("roundID"));
        Round round;
        try {
            round = roundProvider.findRoundByID(roundID).get();
        }
        catch(Exception e) {
            return Results.redirect(router.getReverseRoute(GameController.class, "selectGametype"));
        }

        List<ActiveGame> activeGames = activeGamesService.getActiveGames();

        int myGamePosition = -1;

        for(int i = 0; i < activeGames.size(); i++)
        {
            if(activeGames.get(i).getRound().getID() == round.getID())
                myGamePosition = i;
        }

        if(myGamePosition != -1) {

            ActiveGame oldMyGame = activeGamesService.getGameAt(myGamePosition);
            ActiveGame newMyGame = activeGamesService.getGameAt(myGamePosition);

            Date oldDate = oldMyGame.getDate();
            Date newDate = newMyGame.getDate();

            while (oldDate.compareTo(newDate) == 0 && oldMyGame == newMyGame) {
                try {
                    Thread.sleep(100);
                    newMyGame = activeGamesService.getGameAt(myGamePosition);
                    newDate = newMyGame.getDate();
                } catch (Exception e) {
                    break;
                }
            }
        }

        List<String> players = activeGamesService.getGameUsernames(round);

        if (players.size() == 0) {
            List<Game> games = gameProviders.findGamesByRoundID(round.getID());
            result.render("waiting", 0);
            result.render("games", games);

            return result;
        }

        result.render("waiting", 1);
        result.render("players", players);
        return result;
    }

    public Result newMultiplayerRefresh(Context context)
    {
        Result result = Results.html();

        String temp = context.getParameter("user");
        result.render("user", temp);
        Round round = activeGamesService.getHostedRound(temp);

        List<String> oldPlayers= activeGamesService.getGameUsernames(round);
        List<String> newPlayers= activeGamesService.getGameUsernames(round);

        while(oldPlayers.size() == newPlayers.size())
        {
            try {
                Thread.sleep(100);
                newPlayers= activeGamesService.getGameUsernames(round);
            } catch (Exception e) {

            }
        }

        result.render("roundID", round.getID());
        result.render("players", newPlayers);

        return result;
    }

    @FilterWith(SecureFilter.class)
    public Result newMultiplayerGame(Context context)
    {
        Result result = Results.html();
        String temp = context.getParameter("user");
        result.render("user", temp);
        Round round = activeGamesService.getHostedRound(temp);
        if(round == null) {
            String winner = "NA";
            int winnerNum = -1;
            round = new Round(new Date(), winner, winnerNum);
            roundProvider.persist(round);

            //Add host as a player
            User user = userProvider.findUserByName(temp).get();

            activeGamesService.addActiveGame(round);
            activeGamesService.addUserToGame(round, user);
        }

        List<String> players = activeGamesService.getGameUsernames(round);

        result.render("roundID", round.getID());
        result.render("players", players);

        return result;
    }

    @FilterWith(SecureFilter.class)
    public Result joinGame(Context context) {
        Result result = Results.html();

        int roundID = Integer.parseInt(context.getParameter("roundID"));
        result.render("roundID", roundID);
        Round round;
        try {
            round = roundProvider.findRoundByID(roundID).get();
        }
        catch(Exception e) {
            return Results.redirect(router.getReverseRoute(GameController.class, "selectGametype"));
        }

        if(!round.getWinner().equals("NA"))
        {
            List<Game> games = gameProviders.findGamesByRoundID(round.getID());
            result.render("waiting", 0);
            result.render("games", games);

            return result;
        }
        result.render("waiting", 1);

        String username = context.getParameter("user");
        User user = userProvider.findUserByName(username).get();
        activeGamesService.addUserToGame(round, user);

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
        List<Game> games = gameProviders.findGamesByUsername(username);
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
