package services;

import com.google.inject.Singleton;
import database.Game;
import database.Round;
import database.User;
import services.ActiveGames.ActiveGame;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Albert on 2015-01-23.
 */
@Singleton
public class ActiveGamesService {

    private List<ActiveGame> activeGames;

    ActiveGamesService() {
        activeGames = new LinkedList<>();
    }

    public void addActiveGame(Round round) {
        ActiveGame tempGame = new ActiveGame(round);
        activeGames.add(tempGame);
    }

    public boolean addUserToGame(Round round, User user) {
        for (ActiveGame game : activeGames) {
            if (game.getRound().getID() == round.getID() && !game.contains(user)) {
                game.addUser(user);
                return true;
            }
        }
        return false;
    }

    public void removeActiveGame(int roundID)
    {
        for(ActiveGame game: activeGames)
        {
            if(game.getRound().getID() == roundID)
                activeGames.remove(game);
        }
    }

    public List<ActiveGame> getActiveGames()
    {
        LinkedList<ActiveGame> result = new LinkedList<>();
        for(ActiveGame game: activeGames)
        {
            ActiveGame temp = new ActiveGame(game);
            result.add(temp);
        }
        return result;
    }

    public Round getHostedRound(String username)
    {
        for(ActiveGame game: activeGames)
        {
            if(game.getUsers().size() > 0 && game.getUsers().get(0).equals(username))
                return game.getRound();
        }

        return null;
    }

    public int getRoundIndex(Round round)
    {
        int count = 0;
        for(ActiveGame game:activeGames)
        {
            if(game.getRound().getID() == round.getID())
                return count;

            count++;
        }

        return -1;
    }

    public List<String> getGameUsernames(Round round)
    {
        List<String> result = new LinkedList<>();

        for (ActiveGame game : activeGames) {
            if (game.getRound().getID() == round.getID()) {
                result = game.getUsers();
            }
        }
        return result;
    }

    public Round getRoundAts(int pos)
    {
        return activeGames.get(pos).getRound();
    }

}
