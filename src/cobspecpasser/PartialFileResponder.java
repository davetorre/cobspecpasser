package cobspecpasser;

import httpserver.HTTPRequest;
import httpserver.HTTPResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.*;

public class PartialFileResponder extends FileResponder {

    public byte[] getPartialFileContent(int[] range) {
        byte[] content = {};
        try {
            content = Files.readAllBytes(filePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        content = Arrays.copyOfRange(content, range[0], range[1]);

        return content;
    }

    public String getFirstRegex(String input, String regex) {
        String occurence = "";
        Pattern patt = Pattern.compile(regex);
        Matcher m = patt.matcher(input);
        if (m.find()) {
            occurence = m.group();
        }
        return occurence;
    }

    public int[] getRange(String rangeHeader) {
        int[] range = {0, 0};
        String s = getFirstRegex(rangeHeader, "[0-9]+-[0-9]+");

        if (!s.equals("")) {
            String[] split = s.split("-");
            range[0] = Integer.parseInt(split[0]);
            range[1] = Integer.parseInt(split[1]);
        }

        return range;
    }

    @Override
    public HTTPResponse respond(HTTPRequest request) {
        HTTPResponse response;
        if (request.headers.containsKey("Range")) {
            String rangeHeader = request.headers.get("Range");
            int[] range = getRange(rangeHeader);

            response = new HTTPResponse("HTTP/1.1 206 Partial Content",
                                        new HashMap<String, String>(),
                                        getPartialFileContent(range));
        } else {
            response = super.respond(request);
        }
        return response;
    }

    public PartialFileResponder(Path filePath) {
        super(filePath);
    }
}
