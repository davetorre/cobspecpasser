package cobspecpasser;

import java.util.Arrays;
import java.util.List;

public class ArgParser {

    public static int getPort(String[] args) {
        List<String> argsList = Arrays.asList(args);
        int index = argsList.indexOf("-p");
        return Integer.parseInt(argsList.get(index + 1));
    }

    public static String getDirectory(String[] args) {
        List<String> argsList = Arrays.asList(args);
        int index = argsList.indexOf("-d");
        return argsList.get(index + 1);
    }
}
