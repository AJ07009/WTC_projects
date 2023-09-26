package app.server;

import io.javalin.Javalin;

public class WeShareExpenseService {
    private final Javalin server;
    private final int EXPENSE_PORT = 7070;

    public WeShareExpenseService() {
        server = Javalin.create();

        ExpenseServiceAPIHandler apiHandler = new ExpenseServiceAPIHandler();

        server.get("/expenses/{id}", context -> apiHandler.getExpenseByID(context));
        server.get("/expenses", context -> apiHandler.getExpensesByEmail(context));
        server.get("/person", context -> apiHandler.getInfoForPerson(context));
        server.post("/expenses", context -> apiHandler.expenseController(context));
    }

    public void start(int port) {
        int listen_port = port > 0 ? port : EXPENSE_PORT;
        this.server.start(listen_port);
    }

    public void stop() {
        this.server.stop();
    }

    public static void main(String[] args) {
        WeShareExpenseService server = new WeShareExpenseService();
        server.start(server.EXPENSE_PORT);
    }

}
