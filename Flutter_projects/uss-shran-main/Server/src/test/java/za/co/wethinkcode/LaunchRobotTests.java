package za.co.wethinkcode;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.Server.RobotWorldClient;
import za.co.wethinkcode.Server.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

class LaunchRobotTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient2 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient3 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient4 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient5 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient6 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient7 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient8 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient9 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient1 = new RobotWorldJsonClient();


    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient2.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient3.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient4.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient5.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient6.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient7.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient8.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient9.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient1.connect(DEFAULT_IP, DEFAULT_PORT);
    }


    @AfterEach
    void disconnectFromServer() throws InterruptedException {
        serverClient.disconnect();
        serverClient2.disconnect();
        serverClient3.disconnect();
        serverClient4.disconnect();
        serverClient5.disconnect();
        serverClient6.disconnect();
        serverClient7.disconnect();
        serverClient8.disconnect();
        serverClient9.disconnect();
        serverClient1.disconnect();
        Thread.sleep(1000);
    }

    @Test
    void validLaunchShouldSucceed(){
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
        assertEquals(0, response.get("data").get("position").get(0).asInt());
        assertEquals(0, response.get("data").get("position").get(1).asInt());

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
    }

    @Test
    void invalidLaunchShouldFail(){

        //Note: This wont work for when testing 2x2

        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +"\"robot\": \"HAL\"," +"\"command\": \"luanch\"," +"\"arguments\": [\"shooter\",\"5\",\"5\"]" +"}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }

    @Test
    void tooManyPeeps(){
        assertTrue(serverClient.isConnected());
        String request = "{" + "  \"robot\": \"Jason\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response = serverClient.sendRequest(request);
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        assertEquals(0, response.get("data").get("position").get(0).asInt());
        assertEquals(0, response.get("data").get("position").get(1).asInt());
        assertNotNull(response.get("state"));

        assertTrue(serverClient2.isConnected());
        String requests  = "{" + "  \"robot\": \"Eve\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses = serverClient2.sendRequest(requests);
        assertNotNull(responses.get("result"));
        assertEquals("ERROR", responses.get("result").asText());
        assertNotNull(responses.get("data"));
    }

//    @Test
//    void tooManyNames(){
//        assertTrue(serverClient.isConnected());
//        String request = "{" + "  \"robot\": \"Jason\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
//        JsonNode response = serverClient.sendRequest(request);
//        assertNotNull(response.get("result"));
//        assertEquals("OK", response.get("result").asText());
//        assertNotNull(response.get("data"));
//        assertNotNull(response.get("data").get("position"));
//        assertEquals(0, response.get("data").get("position").get(0).asInt());
//        assertEquals(0, response.get("data").get("position").get(1).asInt());
//        assertNotNull(response.get("state"));assertTrue(serverClient2.isConnected());
//
//        assertTrue(serverClient2.isConnected());
//        String requests  = "{" + "  \"robot\": \"Jason\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
//        JsonNode responses = serverClient2.sendRequest(requests);
//        assertNotNull(responses.get("result"));
//        assertEquals("ERROR", responses.get("result").asText());
//        assertNotNull(responses.get("data"));
//
//
//    }
    @Test
    public void worldWithoutObsIsFull(){

        //Note: This won't work when testing for a world with 1x1
        assertTrue(serverClient.isConnected());
        String requests  = "{" + "  \"robot\": \"Jason\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode responses = serverClient.sendRequest(requests);
        assertNotNull(responses.get("result"));
        assertEquals("OK", responses.get("result").asText());
        assertNotNull(responses.get("data"));

        assertTrue(serverClient2.isConnected());
        String request  = "{" + "  \"robot\": \"Antonio\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response = serverClient2.sendRequest(request);
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertNotNull(response.get("data"));

        assertTrue(serverClient3.isConnected());
        String request3  = "{" + "  \"robot\": \"Aidan\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response3= serverClient3.sendRequest(request3);
        assertNotNull(response3.get("result"));
        assertEquals("OK", response3.get("result").asText());
        assertNotNull(response3.get("data"));

        assertTrue(serverClient4.isConnected());
        String request4 = "{" + "  \"robot\": \"Eve\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response4 = serverClient4.sendRequest(request4);
        assertNotNull(response4.get("result"));
        assertEquals("OK", response4.get("result").asText());
        assertNotNull(response4.get("data"));

        assertTrue(serverClient5.isConnected());
        String request5 = "{" + "  \"robot\": \"User5\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response5 = serverClient5.sendRequest(request5);
        assertNotNull(response5.get("result"));
        assertEquals("OK", response5.get("result").asText());
        assertNotNull(response5.get("data"));

        assertTrue(serverClient6.isConnected());
        String request6 = "{" + "  \"robot\": \"User6\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response6 = serverClient6.sendRequest(request6);
        assertNotNull(response6.get("result"));
        assertEquals("OK", response6.get("result").asText());
        assertNotNull(response6.get("data"));

        assertTrue(serverClient7.isConnected());
        String request7 = "{" + "  \"robot\": \"User7\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response7 = serverClient7.sendRequest(request7);
        assertNotNull(response7.get("result"));
        assertEquals("OK", response7.get("result").asText());
        assertNotNull(response7.get("data"));

        assertTrue(serverClient8.isConnected());
        String request8 = "{" + "  \"robot\": \"User8\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response8= serverClient8.sendRequest(request8);
        assertNotNull(response8.get("result"));
        assertEquals("OK", response8.get("result").asText());
        assertNotNull(response8.get("data"));

        assertTrue(serverClient9.isConnected());
        String request9 = "{" + "  \"robot\": \"User9\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response9 = serverClient9.sendRequest(request9);
        assertNotNull(response9.get("result"));
        assertEquals("OK", response9.get("result").asText());
        assertNotNull(response9.get("data"));

        assertTrue(serverClient1.isConnected());
        String request1 = "{" + "  \"robot\": \"Kaylan\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
        JsonNode response1 = serverClient1.sendRequest(request1);
        assertNotNull(response1.get("result"));
        assertEquals("ERROR", response1.get("result").asText());
        assertNotNull(response1.get("data"));
        //assertEquals("No more space in this world",response5.get("result").asText());


    }

//    @Test
//    void anotherOne(){
//        assertTrue(serverClient.isConnected());
//        String request = "{" + "  \"robot\": \"Jason\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
//        JsonNode response = serverClient.sendRequest(request);
//        assertNotNull(response.get("result"));
//        assertEquals("OK", response.get("result").asText());
//        assertNotNull(response.get("data"));
//
//
//
//        assertTrue(serverClient2.isConnected());
//        String requests = "{" + "  \"robot\": \"Eve\"," + "  \"command\": \"launch\"," + "  \"arguments\": [\"shooter\",\"5\",\"5\"]" + "}";
//        JsonNode responses = serverClient2.sendRequest(requests);
//        assertNotNull(responses.get("result"));
//        assertEquals("OK", responses.get("result").asText());
//        assertNotNull(responses.get("data"));
//        assertNotNull(responses.get("data").get("position"));
//        assertEquals(1, responses.get("data").get("position").get(0).asInt());
//        assertEquals(0, responses.get("data").get("position").get(1).asInt());
//        assertNotNull(responses.get("state"));assertTrue(serverClient2.isConnected());
//
//
//    }
}
