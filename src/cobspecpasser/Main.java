package cobspecpasser;

import httpserver.Server;

import java.io.*;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        int port = ArgParser.getPort(args);
        String directory = ArgParser.getDirectory(args);
        Server server;
        CobSpecRouter router = new CobSpecRouter();
        Form form = new Form();

        router.addRoute("GET /", () -> new DirectoryResponder(Paths.get(directory)));
        router.addRoute("GET /file1", () -> new FileResponder(Paths.get(directory + "file1")));
        router.addRoute("GET /image.jpeg", () -> new FileResponder(Paths.get(directory + "image.jpeg")));
        router.addRoute("GET /image.gif", () -> new FileResponder(Paths.get(directory + "image.gif")));
        router.addRoute("GET /image.png", () -> new FileResponder(Paths.get(directory + "image.png")));
        router.addRoute("GET /text-file.txt", () -> new FileResponder(Paths.get(directory + "text-file.txt")));

        router.addRoute("GET /partial_content.txt",
                () -> new PartialFileResponder(Paths.get(directory + "partial_content.txt")));
        router.addRoute("GET /patch-content.txt",
                () -> new FileResponder(Paths.get(directory + "patch-content.txt")));
        router.addRoute("PATCH /patch-content.txt",
                () -> new PatchFileResponder(Paths.get(directory + "patch-content.txt")));

        router.addRoute("GET /form", () -> new ReadFormResponder(form));
        router.addRoute("PUT /form", () -> new WriteFormResponder(form));
        router.addRoute("POST /form", () -> new WriteFormResponder(form));
        router.addRoute("DELETE /form", () -> new DeleteFormResponder(form));

        router.addRoute("OPTIONS /method_options", () -> new OptionsResponder());
        router.addRoute("PUT /file1", () -> new NotAllowedResponder());
        router.addRoute("POST /text-file.txt", () -> new NotAllowedResponder());
        router.addRoute("GET /parameters", () -> new ParametersResponder());
        router.addRoute("GET /redirect", () -> new RedirectResponder());

        server = new Server(router);
        server.serve(port);
    }
}
