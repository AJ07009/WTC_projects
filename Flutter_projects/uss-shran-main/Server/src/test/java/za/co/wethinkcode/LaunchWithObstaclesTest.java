package za.co.wethinkcode;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.JsonNode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import za.co.wethinkcode.Server.RobotWorldClient;
import za.co.wethinkcode.Server.RobotWorldJsonClient;

public class LaunchWithObstaclesTest {
    // Needs to be run in a 2 x 2 world with an obstacle at 1,1
    
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient client = new RobotWorldJsonClient();
    private final RobotWorldClient client2 = new RobotWorldJsonClient();
    private final RobotWorldClient client3 = new RobotWorldJsonClient();
    private final RobotWorldClient client4 = new RobotWorldJsonClient();
    private final RobotWorldClient client5 = new RobotWorldJsonClient();
    private final RobotWorldClient client6 = new RobotWorldJsonClient();
    private final RobotWorldClient client7 = new RobotWorldJsonClient();
    private final RobotWorldClient client8 = new RobotWorldJsonClient();
    private final RobotWorldClient client9 = new RobotWorldJsonClient();
    
    @BeforeEach
    void connectToServer() {
        client.connect(DEFAULT_IP, DEFAULT_PORT);
        client2.connect(DEFAULT_IP, DEFAULT_PORT);
        client3.connect(DEFAULT_IP, DEFAULT_PORT);
        client4.connect(DEFAULT_IP, DEFAULT_PORT);
        client5.connect(DEFAULT_IP, DEFAULT_PORT);
        client6.connect(DEFAULT_IP, DEFAULT_PORT);
        client7.connect(DEFAULT_IP, DEFAULT_PORT);
        client8.connect(DEFAULT_IP, DEFAULT_PORT);
        client9.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() throws InterruptedException{
        Thread.sleep(1000);
        client.disconnect();
        client2.disconnect();
        client3.disconnect();
        client4.disconnect();
        client5.disconnect();
        client6.disconnect();
        client7.disconnect();
        client8.disconnect();
        client9.disconnect();
    }

    @Test
    void robotsShouldNotSpawnOn1_1() {
        assertTrue(client.isConnected());
        String requests  = "{" + "  \"robot\": \"Jason\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses = client.sendRequest(requests);
        assertNotNull(responses.get("result"));
        assertEquals("OK", responses.get("result").asText());
        assertNotNull(responses.get("data"));
        int[] expected = {1,1};
        int[] actual = {responses.get("data").get("position").get(0).asInt(),responses.get("data").get("position").get(1).asInt()};
        assertFalse(Arrays.equals(expected,actual));


        assertTrue(client2.isConnected());
        String requests1  = "{" + "  \"robot\": \"Hal\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses1 = client2.sendRequest(requests1);
        assertNotNull(responses1.get("result"));
        assertEquals("OK", responses1.get("result").asText());
        assertNotNull(responses1.get("data"));
        int[] expected1 = {1,1};
        int[] actual1 = {responses1.get("data").get("position").get(0).asInt(),responses1.get("data").get("position").get(1).asInt()};
        assertFalse(Arrays.equals(expected1,actual1));

        assertTrue(client3.isConnected());
        String requests2  = "{" + "  \"robot\": \"Dummy\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses2 = client3.sendRequest(requests2);
        assertNotNull(responses2.get("result"));
        assertEquals("OK", responses2.get("result").asText());
        assertNotNull(responses2.get("data"));
        int[] expected2 = {1,1};
        int[] actual2 = {responses2.get("data").get("position").get(0).asInt(),responses2.get("data").get("position").get(1).asInt()};
        assertFalse(Arrays.equals(expected2,actual2));
    }

    @Test
    void worldWithObstacleIsFull() {
        assertTrue(client.isConnected());
        String requests  = "{" + "  \"robot\": \"Jason\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses = client.sendRequest(requests);
        assertNotNull(responses.get("result"));
        assertEquals("OK", responses.get("result").asText());
        assertNotNull(responses.get("data"));

        assertTrue(client2.isConnected());
        String requests2  = "{" + "  \"robot\": \"Hal\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses2 = client2.sendRequest(requests2);
        assertNotNull(responses2.get("result"));
        assertEquals("OK", responses2.get("result").asText());
        assertNotNull(responses2.get("data"));

        assertTrue(client3.isConnected());
        String requests3  = "{" + "  \"robot\": \"Dummy\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses3 = client3.sendRequest(requests3);
        assertNotNull(responses3.get("result"));
        assertEquals("OK", responses3.get("result").asText());
        assertNotNull(responses3.get("data"));

        assertTrue(client4.isConnected());
        String requests4  = "{" + "  \"robot\": \"Godo\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses4 = client4.sendRequest(requests4);
        assertNotNull(responses4.get("result"));
        assertEquals("OK", responses4.get("result").asText());
        assertNotNull(responses4.get("data"));

        assertTrue(client5.isConnected());
        String requests5  = "{" + "  \"robot\": \"l\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses5 = client5.sendRequest(requests5);
        assertNotNull(responses5.get("result"));
        assertEquals("OK", responses5.get("result").asText());
        assertNotNull(responses5.get("data"));

        assertTrue(client6.isConnected());
        String requests6  = "{" + "  \"robot\": \"Go\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses6 = client6.sendRequest(requests6);
        assertNotNull(responses6.get("result"));
        assertEquals("OK", responses6.get("result").asText());
        assertNotNull(responses6.get("data"));

        assertTrue(client7.isConnected());
        String requests7  = "{" + "  \"robot\": \"Gog\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses7 = client7.sendRequest(requests7);
        assertNotNull(responses7.get("result"));
        assertEquals("OK", responses7.get("result").asText());
        assertNotNull(responses7.get("data"));

        assertTrue(client8.isConnected());
        String requests8  = "{" + "  \"robot\": \"Gogo\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses8 = client8.sendRequest(requests8);
        assertNotNull(responses8.get("result"));
        assertEquals("OK", responses8.get("result").asText());
        assertNotNull(responses8.get("data"));

        assertTrue(client9.isConnected());
        String requests9  = "{" + "  \"robot\": \"Gogoo\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses9 = client9.sendRequest(requests9);
        assertNotNull(responses9.get("result"));
        assertEquals("ERROR", responses9.get("result").asText());
        assertNotNull(responses9.get("data"));
        assertEquals("No more space in this world", responses9.get("data").get("message").asText());

    }
}
