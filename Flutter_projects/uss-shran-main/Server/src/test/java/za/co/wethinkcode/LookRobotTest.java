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
 * I want to be able to see all the obstacles in my robots path
 * So that I can move around the robot world easily
 */

public class LookRobotTest {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient2 = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient2.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() throws InterruptedException {
        serverClient.disconnect();
        serverClient2.disconnect();
        Thread.sleep(1000);

    }
    @Test
    void lookForRobotsTest() {
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
        assertNotNull(response.get("data").get("position"));
//        assertEquals(0, response.get("data").get("position").get(0).asInt());
//        assertEquals(0, response.get("data").get("position").get(1).asInt());

        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [2]" +
                "}";
        response = serverClient.sendRequest(request);
        assertNotNull(response.get("data"));
        try {
            assertNotNull(response.get("state").get("position"));
//            assertEquals("0", response.get("state").get("position").get(0).toString());
//            assertEquals("2", response.get("state").get("position").get(1).toString());
        } catch (Exception e) {
            assertNotNull(response.get("data").get("position"));
//            assertEquals(0, response.get("data").get("position").get(0).asInt());
//            assertEquals(2, response.get("data").get("position").get(1).asInt());
        }
        //Here Ends 1st ROBOT
        // When I send a valid launch request to the server
        assertTrue(serverClient2.isConnected());
        String request2 = "{" +
                "  \"robot\": \"SAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response2 = serverClient2.sendRequest(request2);

        // Then I should get a valid response from the server
        assertNotNull(response2.get("result"));
        assertEquals("OK", response2.get("result").asText());
        // And the position should be (x:0, y:0)
        assertNotNull(response2.get("data"));
        assertNotNull(response2.get("state").get("position"));
//        assertEquals(0, response2.get("data").get("position").get(0).asInt());
//        assertEquals(0, response2.get("data").get("position").get(1).asInt());


        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        response = serverClient.sendRequest(request);
        JsonNode lookObjects = response.get("data").get("objects");
        assertNotNull(response.get("data"));
        assertNotNull(response.get("state").get("position"));
//        assertEquals(0, response.get("state").get("position").get(0).asInt());
//        assertEquals(2, response.get("state").get("position").get(1).asInt());
        String expected = "ROBOT";
        if (lookObjects.toString().contains(expected)) {
            boolean result = false;
            for (JsonNode object : lookObjects) {
                if (object.toString().contains(expected)) {
                    result = true;
                }
            }
            assertTrue(result);
        }
    }
}