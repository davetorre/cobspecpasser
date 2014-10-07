package cobspecpasser;

public class Log {
    private String contents = "";

    public synchronized String getContents() {
        return contents;
    }

    public synchronized void writeAtEnd(String data) {
        contents += data + "\n";
    }
}
