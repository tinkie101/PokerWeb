/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;


import controllers.ApplicationController;
import controllers.GameController;
import controllers.UserController;
import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {
        router.GET().route("/").with(ApplicationController.class, "index");
        router.GET().route("/register").with(UserController.class, "register");
        router.GET().route("/logout").with(UserController.class, "logout");
        router.GET().route("/username").with(UserController.class, "username");
        router.GET().route("/game").with(GameController.class, "selectGametype");
        router.GET().route("/multiplayer").with(GameController.class, "multiplayer");
        router.GET().route("/selectGameType").with(GameController.class, "selectGametype");
        router.GET().route("/selectUsers").with(GameController.class, "selectUsers");
        router.GET().route("/activeGames").with(GameController.class, "activeGames");
        router.GET().route("/singleHistory").with(GameController.class, "singleHistory");
        router.GET().route("/history").with(ApplicationController.class, "history");
        router.POST().route("/newMultiplayerGame").with(GameController.class, "newMultiplayerGame");
        router.POST().route("/game").with(GameController.class, "game");
        router.POST().route("/login").with(UserController.class, "login");
        router.POST().route("/newRegister").with(UserController.class, "newRegister");
        router.POST().route("/joinGame").with(GameController.class, "joinGame");
        router.POST().route("/activeJoinGames").with(GameController.class, "activeJoinGames");

 
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        //router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
