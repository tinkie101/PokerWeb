package services.ActiveGames;

import database.Round;
import database.User;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Albert on 2015-01-26.
 */
public class ActiveGame {
    private Round round;
    private List<User> users;

    private Date lastUpdate;

    public ActiveGame(Round round) {
        this.round = round;
        users = new LinkedList<>();
        lastUpdate = new Date();
    }

    public ActiveGame(ActiveGame game)
    {
        this.round = game.round;
        this.users = game.users;
        lastUpdate = new Date();

    }

    public void addUser(User user) {
        lastUpdate = new Date();
        users.add(user);
    }

    public Round getRound()
    {
        return round;
    }


    public List<String> getUsers()
    {
        LinkedList<String> result = new LinkedList<>();

        for(User user: users)
        {
            result.add(user.getUsername());
        }
        return result;
    }

    public boolean contains(User _user)
    {
        for(User user: users)
        {
            if(user.getUsername().equals(_user.getUsername()))
                return true;
        }
        return false;
    }

    public Date getDate()
    {
        return lastUpdate;
    }
}
