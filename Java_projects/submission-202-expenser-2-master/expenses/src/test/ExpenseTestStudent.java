import app.server.WeShareExpenseService;
import kong.unirest.Unirest;

import kong.unirest.HttpResponse;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

class ExpenseTests {
    private static final int TEST_SERVER_PORT = 7070;
    private static final String BASE_URL = "http://localhost:" + TEST_SERVER_PORT;
    private Object TestData;
    private static WeShareExpenseService server;

    @BeforeAll
    public static void startServer() {
        server = new WeShareExpenseService();
        server.start(TEST_SERVER_PORT);
    }

    @AfterAll
    public static void stopServer() {

        server.stop();
    }
    @Test
    @DisplayName("Server status: GET /expenses")
    void testGetListOfExpensesByNotFoundEmail() {
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/expenses?email=false@wethinkcode.co.za").asString();
        assertThat(resp.getStatus()).isEqualTo(400);
        assertThat(resp.getBody()).isEqualTo("Email not found: false@wethinkcode.co.za");
    }


    @Test
    @DisplayName("Server status: GET /expenses")
    void testGetListOfExpensesByFoundEmail() {
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/expenses?email=herman@wethinkcode.co.za").asString();
        assertThat(resp.getStatus()).isEqualTo(200);
        assertThat(resp.getBody()).isNotEmpty();
    }

    @Test
    @DisplayName("Server status: GET /expenses/{id}")
    void testExpensesByValidAndNotFoundID() {
        UUID id = UUID.randomUUID();
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/expenses/" + id).asString();
        assertThat(resp.getStatus()).isEqualTo(404);
        assertThat(resp.getBody()).isEqualTo("Expense not found: " + id);
    }

    @Test
    @DisplayName("Server status: GET /person")
    void testInfoForPersonNotFoundEmail() {
        HttpResponse<String> resp = Unirest.get(BASE_URL + "/person?email=false@wethinkcode.co.za").asString();
        assertThat(resp.getStatus()).isEqualTo(400);
        assertThat(resp.getBody()).isEqualTo("Email not found: false@wethinkcode.co.za");
    }
}