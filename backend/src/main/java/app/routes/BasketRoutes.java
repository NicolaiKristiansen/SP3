package app.routes;

import app.controllers.BasketController;
import app.security.routes.SecurityRoutes.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class BasketRoutes {
    BasketController basketController = new BasketController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", basketController::addProductToBasket, Role.USER);
            post("/buy/{id}", basketController::buyBasket);
        };
    }
}
