package database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by Albert on 2015-01-21.
 */
@Entity
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int roundID;

    public Round() {
    }

}
