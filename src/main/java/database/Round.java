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

    @Column(name = "winner")
    @NotNull
    private String winner;

    @Column(name = "winnerNum")
    @NotNull
    private int winnerNum;

    public Round() {
    }

    public Round(Date date, String winner, int winnerNum) {
        this.winner = winner;
        this.date = date;
        this.winnerNum = winnerNum;
    }

    public int getID() {
        return roundID;
    }

    public Date getDate(){
        return date;
    }

    public void setWinner(String winner)
    {
        this.winner = winner;
    }

    public String getWinner()
    {
        return winner;
    }

    public void setWinnerNum(int num)
    {
        this.winnerNum = num;
    }

    public int getWinnerNum()
    {
        return winnerNum;
    }

}
