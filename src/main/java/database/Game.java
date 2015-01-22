package database;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Albert on 2015-01-21.
 */
@Entity
@Table
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "gameID")
    private int gameID;

    @Size(max = 16)
    @NotNull
    @Column(name = "hand")
    private String hand;

    @Size(max = 255)
    @NotNull
    @Column(name = "evaluate")
    private String evaluate;

    @ManyToOne
    @JoinColumn(name = "username")
    @NotNull
    private User username;

    @ManyToOne
    @JoinColumn(name = "roundID")
    @NotNull
    private Round roundID;

    Game() {
    }

    public Game(String hand, String evaluate, User user, Round round) {
        this.hand = hand;
        this.evaluate = evaluate;
        this.username = user;
        this.roundID = round;
    }

    public String getHand()
    {
        return hand;
    }

    public String getEvaluate()
    {
        return evaluate;
    }

    public int getRoundID()
    {
        return roundID.getID();
    }

    public Date getDate()
    {
        return roundID.getDate();
    }

    public String getUsername()
    {
        return username.getUsername();
    }

}
