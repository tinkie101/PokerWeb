package services.ActiveGames;

import database.Round;
import database.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Albert on 2015-01-26.
 */
public class ActiveGame {
    private Round round;
    private List<User> users;
    private int count;

    public ActiveGame(Round round) {
        this.round = round;
        count = 0;
        users = new LinkedList<>();
    }

    public void addUser(User user) {
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
}
