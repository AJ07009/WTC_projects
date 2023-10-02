package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestDatabase implements QuoteDB {
    private Map<Integer, Quote> quotes;


    private static final String CONN = "jdbc:sqlite:QuoteDatabase.db";

    private static Connection connect() {

        Connection conn = null;
        if(isSuitableDriverAvailable()) {
            try {
                conn = DriverManager.getConnection(CONN);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.err.println("The driver was not correctly loaded and execution was aborted");
        }
        return conn;
    }

    private static boolean isSuitableDriverAvailable() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch(ClassNotFoundException ex) {
            return false;
        }

        return true;
    }
    
//    @Override
//    public Quote get(Integer id) throws SQLException {
//        Quote quote = new Quote();
//        Connection connect = connect();
//        Statement statement = null;
//        String sql = "SELECT * FROM  QuoteData WHERE '"+id+"' = id";
//
//        try {
//            statement = connect.createStatement();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        ResultSet results = statement.executeQuery(sql);
//
//        while(results.next()){
//            System.out.println("Rummaging through the notes...");
//            if (id == results.getInt("id")){
//                quote.setId(results.getInt("id"));
//                quote.setText(results.getString("text"));
//                quote.setName(results.getString("name"));
//                return quote;
//            }
//
//        }
//        System.out.println("issue is here") ;
//        return null;
//    }

    @Override
    public List<Quote> all() throws SQLException {
        String sql = "SELECT id,\n" +
                "          name,\n" +
                "          text \n" +
                "      FROM QuoteData;";
        // creates new ArrayList        
        List<Quote> lQuotes = new ArrayList<>();
        Quote newQuote = new Quote();
        Statement statement;
        try (Connection conn = connect()) {
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " 
                + rs.getString("name") + " " 
                + rs.getString("text"));

                newQuote.setId(rs.getInt("id"));
                newQuote.setName(rs.getString("name"));
                newQuote.setText(rs.getString("text"));
                lQuotes.add(newQuote);
                newQuote = new Quote();
            }   
        } 
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return lQuotes;
    }

    @Override
    public Quote add(Quote quote) throws SQLException {
        addd(quote);
        return quote;
    }

    public Quote addd(Quote quote) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:QuoteDatabase.db");


        final PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO QuoteData (name,text) VALUES (?,?)"
        );
        stm.setString(1, quote.getName());
        stm.setString(2, quote.getText());
        try{stm.execute();}
        catch (SQLException e){

            System.out.println(e);
        }

        return quote;
    }
}