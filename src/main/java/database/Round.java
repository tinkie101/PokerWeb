package database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Albert on 2015-01-21.
 */
@Entity
@Table
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "roundID")
    private int roundID;

    public Round() {
    }

    public int getID() {
        return roundID;
    }

}
