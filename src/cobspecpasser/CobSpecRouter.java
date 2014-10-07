package cobspecpasser;

import httpserver.Responder;
import httpserver.Router;
import httpserver.ResponderCreator;

import java.util.HashMap;

public class CobSpecRouter implements Router {
    private Log logs = new Log();

    public HashMap<String, ResponderCreator> routes = new HashMap<>();

    public void addRoute(String name, ResponderCreator rc) {
        routes.put(name, rc);
    }

    public synchronized Responder getResponder(String routeName) {
        logs.writeAtEnd(routeName + " HTTP/1.1");

        Responder responder = new httpserver.NotFoundResponder();
        if (routes.containsKey(routeName)) {
            responder = routes.get(routeName).create();
        }
        return responder;
    }

    public CobSpecRouter() {
        addRoute("GET /logs", () -> new AuthLogResponder(logs, "admin", "hunter2"));
    }
}
