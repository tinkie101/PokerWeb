/**
 * Copyright (C) 2013 the original author or authors.
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

package controllers;

import com.google.inject.Inject;
import ninja.Context;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;
import ninja.Router;
import poker.cards.Hand;
import poker.generator.Generator;
import services.IPokerService;
import services.PokerService;


@Singleton
public class ApplicationController {

    public final String USERNAME = "username";

    @Inject
    Router router;

    public Result index(Context context)
    {
        if (context.getSession() != null && context.getSession().get(USERNAME) != null) {

            return Results.redirect(router.getReverseRoute(GameController.class, "game"));
        }

        Result result = Results.html();
        return result;
    }

    public static class SimplePojo {

        public String content;
        
    }

}
