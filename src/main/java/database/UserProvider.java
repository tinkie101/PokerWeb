package database;

import ninja.jpa.UnitOfWork;

import java.util.List;
import java.util.Optional;

/**
 * Created by Albert on 2015-01-20.
 */
public class UserProvider extends BaseRepository {

    @UnitOfWork    //@transaction is for writes
    public Optional<User> findUserByName(String name){
        return getSingleResult(getEntityManager().createQuery("SELECT u FROM User u WHERE u.username = :name").setParameter("name", name));
    }

    @UnitOfWork    //@transaction is for writes
    public List<User> getAllUsers() {
        return getEntityManager().createQuery("SELECT u FROM User u").getResultList();
    }

    public boolean exists(String name)
    {
        return findUserByName(name).isPresent();
    }
}
