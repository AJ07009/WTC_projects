package za.co.wethinkcode;


import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.RobotWorldClient;
import za.co.wethinkcode.Server.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * As a player
 * I want to be able to see all the obstacles in my robot's path
 * So that I can move around the robot world easily
 */

public class LookEmptyTest {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }
    @Test
    void lookEmptyWorld() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
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
        boolean result = !lookObjects.toString().contains("OBSTACLE") && !lookObjects.toString().contains("Robot");
        assertTrue(result);
    }

}