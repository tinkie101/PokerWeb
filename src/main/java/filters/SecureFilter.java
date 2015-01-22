package filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import controllers.ApplicationController;
import ninja.*;

/**
 * Created by Albert on 2015-01-16.
 */
@Singleton
public class SecureFilter implements Filter {

    public final String USERNAME = "username";

    @Inject
    Router router;

    @Override
    public Result filter(FilterChain chain, Context context) {
        if (context.getSession() == null || context.getSession().get(USERNAME) == null) {
            context.getFlashScope().error("Invalid Login Credentials!");
            return Results.redirect(router.getReverseRoute(ApplicationController.class, "index"));

        } else {
            return chain.next(context);
        }

    }
}
