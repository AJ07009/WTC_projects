package za.co.wethinkcode.lms;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.linklists.LinkListsApp;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ShortenUrlTest {
    private static LinkListsApp app = new LinkListsApp();

    @BeforeAll
    static void setupAll() {
        app.start(0);
    }

    @Test
    void testShortenUrlRequestReturnsAppropriateShortUrl() {
        List<Integer> counts = List.of(1, 2, 3);
        counts.forEach((c) -> {
            JsonNode data = new JsonNode("{\"long\": \"https://https://collaboration.csc.ncsu.edu/laurie/Papers/XPSardinia.PDF\", \"max_words\":"
                    + c + "}");

            HttpResponse<JsonNode> resp = Unirest.post("http://localhost:" + app.port() + "/remix").body(data).asJson();

            assertThat(resp.getStatus()).isEqualTo(201);

            String shortened = resp.getBody().getObject().getString("short");
            assertThat(shortened.split("-").length).isEqualTo(c);

        });

    }
}
