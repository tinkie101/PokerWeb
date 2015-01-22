package database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Albert on 2015-01-22.
 */
public class History {

    @Size(max = 16)
    @NotNull
    @Column(name = "hand")
    private String hand;

    @Size(max = 255)
    @NotNull
    @Column(name = "evaluate")
    private String evaluate;

    @Column
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public History(String hand, String evaluate, Date date) {
        this.hand = hand;
        this.evaluate = evaluate;
        this.date = date;
    }

    public Date getDate()
    {
        return date;
    }

    public String getHand()
    {
        return hand;
    }

    public String getEvaluate()
    {
        return evaluate;
    }
}
