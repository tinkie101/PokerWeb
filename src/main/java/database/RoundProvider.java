package database;

import ninja.jpa.UnitOfWork;

import java.util.Optional;

/**
 * Created by Albert on 2015-01-21.
 */
public class RoundProvider extends BaseRepository {

    @UnitOfWork    //@transaction is for writes
    public Optional<User> findRoundByID(Integer ID) {
        return getSingleResult(getEntityManager().createQuery("SELECT r FROM Round r WHERE r.roundID = :ID").setParameter("ID", ID));
    }

    public boolean exists(Integer ID) {
        return findRoundByID(ID).isPresent();
    }
}
