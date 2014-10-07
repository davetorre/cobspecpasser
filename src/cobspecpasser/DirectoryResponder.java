package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;
import httpserver.Responder;

import java.io.File;
import java.util.HashMap;
import java.nio.file.*;

public class DirectoryResponder implements Responder {
    private Path path;

    public byte[] getDirContent() {
        File directory = path.toFile();
        String[] fileList = directory.list();
        String dirContent = "";

        for (String fileName : fileList) {
            dirContent += "<a href=/" + fileName + ">" + fileName + "</a>\n";
        }

        return dirContent.getBytes();
    }

    public HTTPResponse respond(HTTPRequest request) {
        return new HTTPResponse("HTTP/1.1 200 OK",
                                new HashMap<String, String>(),
                                getDirContent());
    }

    public DirectoryResponder(Path path) {
        this.path = path;
    }
}
