package app.database;

import java.sql.SQLException;
import java.util.List;

public interface QuoteDB {
    /**
     * Get a single quote by id.
     * @param id the Id of the quote
     * @return a Quote
     */
    Quote get(Integer id) throws SQLException;

    /**
     * Get all quotes in the database
     * @return A list of quotes
     * @throws SQLException
     */
    List<Quote> all() throws SQLException, SQLException;

    /**
     * Add a single quote to the database.
     * @param quote the quote to add
     * @return the newly added Quote
     * @throws SQLException
     */
    Quote add(Quote quote) throws SQLException;
}
