package database;

import ninja.jpa.UnitOfWork;

import java.util.List;
import java.util.Optional;

/**
 * Created by Albert on 2015-01-21.
 */
public class RoundProvider extends BaseRepository {

    @UnitOfWork    //@transaction is for writes
    public Optional<Round> findRoundByID(Integer ID) {
        return getSingleResult(getEntityManager().createQuery("SELECT r FROM Round r WHERE r.roundID = :ID").setParameter("ID", ID));
    }

    @UnitOfWork    //@transaction is for writes
    public List<Round> findAllRounds() {
        return getEntityManager().createQuery("SELECT r FROM Round r").getResultList();
    }

    public boolean exists(Integer ID) {
        return findRoundByID(ID).isPresent();
    }
}
