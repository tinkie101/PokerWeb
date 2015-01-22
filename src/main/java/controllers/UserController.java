package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import database.PasswordHashing;
import database.User;
import database.UserProvider;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.Router;

/**
 * Created by Albert on 2015-01-16.
 */
@Singleton
public class UserController {

    @Inject
    Router router;

    @Inject
    UserProvider userProvider;

    public final String USERNAME = "username";

    public Result register(Context context)
    {
        Result result = Results.html();

        return result;
    }

    public Result newRegister(Context context)
    {
        User user = new User(context.getParameter("Username"), context.getParameter("Password"), PasswordHashing.generateSalt());

        if(userProvider.exists(user.getUsername())) {
            context.getFlashScope().error("Registration was NOT Successful!");
        }
        else
        {
            userProvider.persist(user);
        }
        return Results.redirect(router.getReverseRoute(ApplicationController.class, "index"));
    }

    public Result login(Context context)
    {
        String username = context.getParameter("Username");
        String password = context.getParameter("Password");

        if(userProvider.exists(username))
        {
            User user = userProvider.findUserByName(username).get();

            if(PasswordHashing.generateHash(password, user.getSalt()).equals(user.getPassword()))
                context.getSession().put(USERNAME, context.getParameter("Username"));
        }

        return Results.redirect(router.getReverseRoute(GameController.class, "selectUsers"));
    }

    public Result logout(Context context)
    {
        context.getSession().clear();
        return Results.redirect(router.getReverseRoute(ApplicationController.class, "index"));
    }

    public Result username(Context context)
    {
        String username = context.getParameter(USERNAME);

        boolean exists = userProvider.exists(username);

        String existsString = "false";

        if(exists)
            existsString = "true";

        Result result = Results.html().render("result", existsString);

        return result;
    }
}
