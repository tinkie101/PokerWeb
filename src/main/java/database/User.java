package database;

import org.apache.commons.codec.binary.Base64;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 * Created by Albert on 2015-01-20.
 */
@Entity
public class User {

    @Id
    @Size (max = 12)
    private String username;

    private String password;
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
