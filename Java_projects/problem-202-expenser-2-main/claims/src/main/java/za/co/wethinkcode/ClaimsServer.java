package za.co.wethinkcode;

import io.javalin.Javalin;
import za.co.wethinkcode.claim_server.ClaimsApiController;

import java.net.UnknownHostException;



public class ClaimsServer {
    private static final int CLAIMS_PORT = 5050;
    private final Javalin server;

    public ClaimsServer() {
        server = Javalin.create(config -> {
            config.defaultContentType = "application/json";
            config.enableCorsForOrigin("");
        });

        this.server.get("/claim/{id}", context -> ClaimsApiController.getClaimId(context));
        this.server.put("/claim", context -> ClaimsApiController.updateClaim(context));
        this.server.post("/claim", context -> ClaimsApiController.create(context));

        this.server.get("/claims/from/{email}?{settled=value}", context -> ClaimsApiController.getClaimsFrom(context));
        this.server.get("/claims/by/{email}?{settled=value}", context -> ClaimsApiController.getClaimsBy(context));

        this.server.post("/settlement", context -> ClaimsApiController.create(context));

        this.server.get("/totalclaimvalue/from/{email}", context -> ClaimsApiController.getTotalClaimValueFrom(context));
        this.server.get("/totalclaimvalue/by/{email}", context -> ClaimsApiController.getTotalClaimValueBy(context));
    }

    public static void main(String[] args) throws UnknownHostException {
        ClaimsServer server = new ClaimsServer();
        server.start(CLAIMS_PORT);
    }

    public void start(int port) {
        this.server.start(port);
    }

    public void stop() {
        this.server.stop();
    }
    public String port(){return String.valueOf(this.CLAIMS_PORT);}

}
