package za.co.wethinkcode;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.RobotWorldClient;
import za.co.wethinkcode.Server.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

public class LookObsTest {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() throws InterruptedException {
        Thread.sleep(1000);
        serverClient.disconnect();
    }
    @Test
    void lookForObsTest() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 2x2 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());


        //When I send a look request to the server
        String lookRequest = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode lookResponse = serverClient.sendRequest(lookRequest);

        // Then I should get a valid response from the server
        assertNotNull(lookResponse.get("result"));
        assertEquals("OK", lookResponse.get("result").asText());

        JsonNode lookObjects = lookResponse.get("data").get("objects");
        String expected2 = "OBSTACLE";
        if (lookObjects.toString().contains(expected2)) {
            boolean result2 = false;
            for (JsonNode object : lookObjects) {
                if (object.toString().contains(expected2)) {
                    result2 = true;
                }
            }
            assertTrue(result2);
        }
    }
}
