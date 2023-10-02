package za.co.wethinkcode;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.*;
import za.co.wethinkcode.Server.ApiServer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiServerTests {
    private static ApiServer server;

    @BeforeAll
    public static void startServer() {
        String[] ary = new String[0];
        server = new ApiServer(ary);
        server.start(9000);
    }

    @AfterAll
    public static void stopServer() {
        server.stop();
    }

    @Test
    @DisplayName("GET /world")
    void getAllQuotes() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("http://localhost:9000/world").asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));
        JSONArray jsonArray = response.getBody().getArray();
        assertEquals(1, jsonArray.length());
    }

    @Test
    @DisplayName("POST /Robot/{name}")
    void postLaunchingRobot(){
        JSONObject myObject = new JSONObject();
        myObject.put("robot", "Hal");
        myObject.put("command", "launch");
        JSONArray values = new JSONArray();
        values.put("sniper");
        values.put("5");
        values.put("5");
        myObject.put("arguments",values);
//        System.out.println(myObject);

        HttpResponse<JsonNode> response = Unirest.post("http://localhost:9000/robot/Hal")
                .header("Content-Type", "application/json")
                .body(myObject)
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));
        JSONArray jsonArray = response.getBody().getArray();
        assertEquals(1, jsonArray.length());
    }

    @Test
    @DisplayName("POST /Robot/{name}")
    void postCommandForwardRobot(){
        JSONObject myObject = new JSONObject();
        myObject.put("robot", "Hal");
        myObject.put("command", "launch");
        JSONArray values = new JSONArray();
        values.put("sniper");
        values.put("5");
        values.put("5");
        myObject.put("arguments",values);
//        System.out.println(myObject);

        HttpResponse<JsonNode> response = Unirest.post("http://localhost:9000/robot/Hal")
                .header("Content-Type", "application/json")
                .body(myObject)
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));
        JSONArray jsonArray;
        //------------------ forward part of test ---------------------
        myObject = new JSONObject();
        myObject.put("robot", "Hal");
        myObject.put("command", "forward");
        values = new JSONArray();
        values.put("5");
        myObject.put("arguments",values);
//        System.out.println(myObject);

        response = Unirest.post("http://localhost:9000/robot/Hal")
                .header("Content-Type", "application/json")
                .body(myObject)
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));
        jsonArray = response.getBody().getArray();
        assertEquals(1, jsonArray.length());
    }

    @Test
    @DisplayName("POST /Robot/{name}")
    void postCommandLookRobot(){
        JSONObject myObject = new JSONObject();
        myObject.put("robot", "Hal");
        myObject.put("command", "launch");
        JSONArray values = new JSONArray();
        values.put("sniper");
        values.put("5");
        values.put("5");
        myObject.put("arguments",values);
//        System.out.println(myObject);

        HttpResponse<JsonNode> response = Unirest.post("http://localhost:9000/robot/Hal")
                .header("Content-Type", "application/json")
                .body(myObject)
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));
        JSONArray jsonArray;
        //------------------ forward part of test ---------------------
        myObject = new JSONObject();
        myObject.put("robot", "Hal");
        myObject.put("command", "look");
        values = new JSONArray();
        values.put("");
        myObject.put("arguments",values);
//        System.out.println(myObject);

        response = Unirest.post("http://localhost:9000/robot/Hal")
                .header("Content-Type", "application/json")
                .body(myObject)
                .asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));
        jsonArray = response.getBody().getArray();
        assertEquals(1, jsonArray.length());
    }
}