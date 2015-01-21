package database;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Albert on 2015-01-21.
 */
public class Games {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int gameID;

    @Size(max = 16)
    @NotNull
    private String hand;

    @Size(max = 255)
    @NotNull
    private String evaluate;

    @ManyToOne
    @Size(max = 255)
    @NotNull
    private String username;

    //TODO
    @ManyToOne
    @NotNull
    private int roundID;

    Games() {
    }

}
