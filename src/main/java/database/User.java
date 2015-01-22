package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Albert on 2015-01-20.
 */
@Entity
@Table
public class User {

    @Id
    @Size(max = 255)
    @NotNull
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    User()
    {
    }

    public User(String username, String password, String salt)
    {
        this.username = username;
        this.salt = salt;

        this.password = PasswordHashing.generateHash(password, salt);
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getSalt()
    {
        return salt;
    }
}
