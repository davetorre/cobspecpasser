package cobspecpasser;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class FormTest {
    @Test
    public void testReadEmptyForm() throws Exception {
        Form myForm = new Form();
        assertTrue(Arrays.equals(new byte[0], myForm.read()));
    }

    @Test
    public void testWriteReadAndDeleteForm() throws Exception {
        Form myForm = new Form();
        myForm.write("Things!".getBytes());
        assertTrue(Arrays.equals("Things!".getBytes(), myForm.read()));
        myForm.delete();
        assertTrue(Arrays.equals(new byte[0], myForm.read()));
    }

}
