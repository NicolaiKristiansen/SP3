package app.routes;

import app.controllers.ProductController;
import app.security.routes.SecurityRoutes.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ProductRoutes {
    ProductController controller = new ProductController();

    public EndpointGroup getRoutes(){
        return () -> {
            post("/", controller::create, Role.ADMIN);
            get("/{id}", controller::findByID, Role.ADMIN, Role.USER);
            get("/", controller::getAll, Role.ADMIN, Role.USER);
            put("/{id}", controller::update, Role.ADMIN);
            delete("/{id}", controller::delete, Role.ADMIN);
        };
    }
}
