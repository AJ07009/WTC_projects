package za.co.wethinkcode.lms;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.linklists.LinkListsApp;

import static org.assertj.core.api.Assertions.assertThat;

public class MotdTest {
    @Test
    void testGetReturnsMessageOfTheDayWhenInjectedOnStart() {
        String motd = "A brand new Message Of The Day";
        LinkListsApp app = new LinkListsApp(motd);
        app.start(0);

        HttpResponse<JsonNode> response = Unirest.get("http://localhost:" + app.port() + "/motd").asJson();

        JsonNode expectedJsonBody = new JsonNode("{\"motd\": \"" + motd + "\"}");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getBody().getObject()).isEqualTo(expectedJsonBody.getObject());
    }

    @Test
    void testGetReturnsDefaultMessageOfTheDayWhenUnspecified(){
        LinkListsApp app = new LinkListsApp();
        app.start(0);

        HttpResponse<JsonNode> response = Unirest.get("http://localhost:" + app.port() + "/motd").asJson();

        JsonNode expectedJsonBody = new JsonNode("{\"motd\": \"~~=[Welcome to the LinkLists Service!]=~~\"}");
        assertThat(response.getBody().getObject()).isEqualTo(expectedJsonBody.getObject());
    }

}
