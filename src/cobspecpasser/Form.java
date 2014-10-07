package cobspecpasser;

public class Form {
    private byte[] contents = new byte[0];

    public synchronized byte[] read() {
        return contents;
    }

    public synchronized void write(byte[] data) {
        String s = new String(data);
        s = s.replace("=", " = ");
        contents = s.getBytes();
    }

    public synchronized void delete() {
        contents = new byte[0];
    }
}
