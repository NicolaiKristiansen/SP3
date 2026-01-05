package app;

import app.config.ApplicationConfig;
import app.config.HibernateConfig;
import app.routes.Routes;
import app.security.routes.SecurityRoutes;
import jakarta.persistence.EntityManagerFactory;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello SP2");

        HibernateConfig.getEntityManagerFactory();

        ApplicationConfig
                .getInstance()
                .initiateServer()
                .checkSecurityRoles() // check for role when route is called
                .setRoute(new SecurityRoutes().getSecurityRoutes())
                .setRoute(SecurityRoutes.getSecuredRoutes())
                .setRoute(new Routes().getRoutes())
                .setRoute(()->{
                    path("/index",()->{
                        get("/",ctx->ctx.render("index.html"));
                    });
                })
                .startServer(7007)
                .setCORS()
                .setGeneralExceptionHandling();
    }
}
