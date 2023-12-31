package za.co.wethinkcode;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.RobotWorldClient;
import za.co.wethinkcode.Server.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class StateRobotTest {
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
    void validStateShouldSucceed(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data"));
        assertNotNull(response.get("state").get("position"));
        assertEquals(0, response.get("data").get("position").get(0).asInt());
        assertEquals(0, response.get("data").get("position").get(1).asInt());

        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"" +
                "}";
        response = serverClient.sendRequest(request);
        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
    }
    @Test
    void invalidStateShouldFail(){
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);
        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data"));
        assertNotNull(response.get("state").get("position"));
        assertEquals(0, response.get("data").get("position").get(0).asInt());
        assertEquals(0, response.get("data").get("position").get(1).asInt());


        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"st\"" +
                "}";
        response = serverClient.sendRequest(request);

        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());
        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }
}