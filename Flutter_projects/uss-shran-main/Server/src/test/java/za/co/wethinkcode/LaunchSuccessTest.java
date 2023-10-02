package za.co.wethinkcode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import za.co.wethinkcode.Server.RobotWorldClient;
import za.co.wethinkcode.Server.RobotWorldJsonClient;

import com.fasterxml.jackson.databind.JsonNode;

public class LaunchSuccessTest {
    private final RobotWorldClient client = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        client.connect("localhost", 5000);
    }

    @AfterEach
    void disconnect() throws InterruptedException {
        Thread.sleep(1000);
        client.disconnect();
    }

    @Test
    void successLaunch() {
        assertTrue(client.isConnected());

        String jsonRequest = "{" + "  \"robot\": \"Dummy\"," + "  \"command\": \"launch\","
                + "  \"arguments\": [\"shooter\",\"4\",\"3\"]" + "}";

        JsonNode serverResponse = client.sendRequest(jsonRequest);

        // Check all response keys are not null
        assertNotNull(serverResponse.get("result"));
        assertNotNull(serverResponse.get("data"));
        assertNotNull(serverResponse.get("data").get("position"));
        assertNotNull(serverResponse.get("state"));
        assertNotNull(serverResponse.get("state").get("position"));
        assertNotNull(serverResponse.get("state").get("shots"));
        assertNotNull(serverResponse.get("state").get("shields"));
        assertNotNull(serverResponse.get("state").get("status"));
        assertNotNull(serverResponse.get("state").get("direction"));

        // Check result is correct
        assertEquals("OK", serverResponse.get("result").asText());

        // Check position is correct
        assertEquals(0, serverResponse.get("data").get("position").get(0).asInt());
        assertEquals(0, serverResponse.get("data").get("position").get(1).asInt());

        // Check state values
//        assertEquals(0, serverResponse.get("state").get("shots").asInt());
//        assertEquals(0, serverResponse.get("state").get("shields").asInt());
//        assertEquals("NORTH", serverResponse.get("state").get("direction").asText());
    }
}
