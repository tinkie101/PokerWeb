package database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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

    @Column
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Round() {
    }

    public Round(Date date) {
        this.date = date;
    }

    public int getID() {
        return roundID;
    }

    public Date getDate(){
        return date;
    }

}
