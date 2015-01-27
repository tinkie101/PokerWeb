package database;

import ninja.jpa.UnitOfWork;

import java.util.List;

/**
 * Created by Albert on 2015-01-27.
 */
public class GameProviders extends BaseRepository{
    @UnitOfWork    //@transaction is for writes
    public List<Game> findGamesByUsername(String username) {
        return getEntityManager().createQuery("SELECT g FROM Game g WHERE g.username.username = :username").setParameter("username", username).getResultList();
    }

    @UnitOfWork    //@transaction is for writes
    public List<Game> findGamesByRoundID(int ID) {
        return getEntityManager().createQuery("SELECT g FROM Game g WHERE g.roundID.roundID = :ID").setParameter("ID", ID).getResultList();
    }

}
