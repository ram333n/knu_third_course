import org.example.Client;
import org.example.Constants;
import org.example.TestClass;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TestApplication {
    @Test
    void testApplication() {
        try {
            Client client = new Client();
            client.connect(Constants.HOST, Constants.PORT);

            TestClass testClass = new TestClass("Testname", 18, LocalDate.now());

            String response = client.sendSerializedObject(testClass);
            assertEquals("Object is deserialized", response);

            response = client.sendSerializedObject(null);
            assertEquals("Error, received null object!", response);

            client.stop();
        } catch (IOException e) {
            fail();
        }
    }
}
