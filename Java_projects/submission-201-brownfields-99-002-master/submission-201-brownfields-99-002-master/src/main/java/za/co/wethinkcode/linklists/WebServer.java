package za.co.wethinkcode.linklists;

public class WebServer {
    private static int LISTEN_PORT = 8000;
    private static LinkListsApp app;

    public static void main(String[] args) {
        app = new LinkListsApp("~~=[Welcome to the LinkLists Service!]=~~");
        app.start(LISTEN_PORT);
    }
}
