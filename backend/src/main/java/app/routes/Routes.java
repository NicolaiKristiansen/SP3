package app.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {
  private BasketRoutes basketRoutes = new BasketRoutes();
  private RecieptRoutes recieptRoutes = new RecieptRoutes();
  private ProductRoutes productRoutes = new ProductRoutes();
  private BasketProductRoutes basketProductRoutes = new BasketProductRoutes();

  public EndpointGroup getRoutes() {
        return () -> {
            get("/", ctx -> ctx.result("Hello World"));
            path("/baskets", basketRoutes.getRoutes());
            path("/receipt", recieptRoutes.getRoutes());
            path("/basketproducts", basketProductRoutes.getRoutes());
            path("/products", productRoutes.getRoutes());
        };
    }
}
