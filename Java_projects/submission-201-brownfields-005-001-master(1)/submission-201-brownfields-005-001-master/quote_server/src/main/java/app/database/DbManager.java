package app.database;

import java.sql.*;

public class DbManager {

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
        return false;
    }

    public static void insert(Integer id, String name, String text) {

        String sql2 = "INSERT INTO [quote] (\n" +
                "                      name,\n" +
                "                      text,\n" + 
                "                          )\n" +
                "                   VALUES (\n" +
                "                         ?,\n" +
                "                         ?,\n" +
                "                           );";

        try (Connection conn = connect();
            PreparedStatement preSTMT = conn.prepareStatement(sql2)){
                preSTMT.setString(1, name);
                preSTMT.setString(2, text);

                preSTMT.execute();
            System.out.println("Message to Mr Matthew");    
        } 
        catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
}