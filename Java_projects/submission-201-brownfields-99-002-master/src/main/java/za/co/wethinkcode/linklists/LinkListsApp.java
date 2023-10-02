package za.co.wethinkcode.linklists;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import za.co.wethinkcode.linklists.motd.MotdResponse;
import za.co.wethinkcode.linklists.words.RecieveWords;
import za.co.wethinkcode.linklists.words.WordsModel;

public class LinkListsApp {
    private static Javalin app;
    private  String messageOfTheDay;
    private static final RecieveWords database = new RecieveWords();

    // <<<
    // COMPLETE THIS CLASS
    // >>>

    public LinkListsApp(String messageOfTheDay) {

        this.app.post("/remix", context -> LinkListApiHandler.remix(context));
        this.messageOfTheDay = messageOfTheDay;

        app = Javalin.create(config -> {
            config.defaultContentType = "application/json";
        });

        this.app.get("/motd", context -> getMotd(context));
    }

    public LinkListsApp() {
        this.messageOfTheDay = "~~=[Welcome to the LinkLists Service!]=~~";

        app = Javalin.create(config -> {
            config.defaultContentType = "application/json";
        });

        this.app.get("/motd", context -> getMotd(context));
    }


    public void getMotd(Context context) {
        MotdResponse motdResponse = new MotdResponse(messageOfTheDay);
        motdResponse.setMotd(messageOfTheDay);
        context.json(motdResponse);
        System.out.println(motdResponse.getMotd());
    }

//

    public void start(int port){
        LinkListsApp.app.start(port);
    }

    public int port(){
        return app.port();
    }

}
