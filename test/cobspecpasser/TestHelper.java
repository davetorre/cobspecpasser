package cobspecpasser;

import java.io.*;

public class TestHelper {
    public static File makeTempFile(String name, byte[] content) throws IOException {
        File tempFile = new File(name);
        tempFile.deleteOnExit();
        InputStream in = new ByteArrayInputStream(content);
        FileOutputStream out = new FileOutputStream(tempFile);

        int nextByte;
        while ((nextByte = in.read()) != -1) {
            out.write(nextByte);
        }
        out.close();

        return tempFile;
    }
}
