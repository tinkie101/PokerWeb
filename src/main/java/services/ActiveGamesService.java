package services;

import com.google.inject.Singleton;
import database.Round;
import database.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Albert on 2015-01-23.
 */
@Singleton
public class ActiveGamesService {

    private List<ActiveGame> activeGames;

    private class ActiveGame{
        protected Round round;
        protected List<User> users;

        ActiveGame(Round round)
        {
            this.round = round;
            users = new LinkedList<>();
        }
    }

    ActiveGamesService()
    {
        activeGames = new LinkedList<>();
    }

    public void addActiveGame(Round round)
    {
        ActiveGame tempGame = new ActiveGame(round);
        activeGames.add(tempGame);
    }

    public boolean addUserToGame(Round round, User user)
    {
        for(ActiveGame game: activeGames)
        {
            if(game.round.equals(round))
            {
                //add user
            }
        }
    }
//
//    public void removeRound(ActiveGame activeGames)
//    {
//        activeGames.remove(activeGames);
//    }
}
