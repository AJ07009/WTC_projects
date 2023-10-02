package app;

import java.sql.SQLException;

import app.database.Quote;
import app.database.QuoteDB;
import app.database.TestDatabase;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;

class QuoteApiHandler {
    private static final QuoteDB database = new TestDatabase();


    
    /**
     * Get all quotes
     *
     * @param context The Javalin Context for the HTTP GET Request
     */
    public static void getAll(Context context) {
        try {
            context.json(database.all());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get one quote
     *
     * @param context The Javalin Context for the HTTP GET Request
     * @throws SQLException
     */
    public static void getOne(Context context) throws SQLException {
        Integer id = context.pathParamAsClass("id", Integer.class).get();
        Quote quote = database.get(id);
        if (quote == null){
            throw new NotFoundResponse("welp there is fokkol here" + id);
        }
        context.json(quote);
    }


    
    /**
     * Create a new quote
     *
     * @param context The Javalin Context for the HTTP POST Request
     * @throws SQLException
     */
    public static void create(Context context) throws SQLException {
        System.out.println("A new quote was created");
        Quote quote = context.bodyAsClass(Quote.class);
        Quote newQuote = database.add(quote);
        System.out.println(newQuote.getName());
        context.status(HttpCode.CREATED);
        // context.json(newQuote);
    }
}
