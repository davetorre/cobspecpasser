package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import httpserver.Responder;

import java.io.IOException;
import java.util.HashMap;
import java.nio.file.*;

public class FileResponder implements Responder {
    Path filePath;

    public byte[] getFileContent() {
        byte[] content = {};
        try {
            content = Files.readAllBytes(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return content;
    }

    public HTTPResponse respond(HTTPRequest request) {
        return new HTTPResponse("HTTP/1.1 200 OK",
                                new HashMap<String, String>(),
                                getFileContent());
    }

    public FileResponder(Path filePath) {
        this.filePath = filePath;
    }
}
