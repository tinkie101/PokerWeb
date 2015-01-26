package services;

import com.google.inject.Singleton;
import database.Game;
import database.Round;
import database.User;
import services.ActiveGames.ActiveGame;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Albert on 2015-01-23.
 */
@Singleton
public class ActiveGamesService {

    private List<ActiveGame> activeGames;
    private Date lastUpdate;

    ActiveGamesService() {
        activeGames = new LinkedList<>();
        lastUpdate = new Date();
    }

    public Date getDate()
    {
        return lastUpdate;
    }

    public void addActiveGame(Round round) {
        lastUpdate = new Date();
        ActiveGame tempGame = new ActiveGame(round);
        activeGames.add(tempGame);
    }

    public boolean addUserToGame(Round round, User user) {
        for (ActiveGame game : activeGames) {
            if (game.getRound().getID() == round.getID() && !game.contains(user)) {
                lastUpdate = new Date();
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
            if(game.getRound().getID() == roundID) {
                lastUpdate = new Date();
                activeGames.remove(game);
            }
        }
    }

    public List<ActiveGame> getActiveGames()
    {
        LinkedList<ActiveGame> result = new LinkedList<>();
        for(ActiveGame game: activeGames)
        {
            result.add(game);
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

}
