package app.routes;

import app.controllers.BasketProductController;
import app.security.routes.SecurityRoutes.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.post;

public class BasketProductRoutes {
    BasketProductController basketProductController = new BasketProductController();

    public EndpointGroup getRoutes() {
        return () -> {
            get("/{id}", basketProductController::getProductsFromBasket, Role.USER);
        };
    }
}
