package za.co.wethinkcode.linklists.words;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecieveWords {
    static final String CONN = "jdbc:sqlite:words.db";

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

    public List<WordsModel> all() throws SQLException {
        ArrayList<WordsModel> words = new ArrayList<>();
        WordsModel newWords = new WordsModel();
        String sql = "SELECT * FROM words\n" +
                "ORDER BY RANDOM()\n" +
                "LIMIT 3";

        Statement statement;

        try (Connection connection = connect()){
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            System.out.println(rs.getInt("id") + " "
                    + rs.getString("word"));

            newWords.setId(rs.getInt("id"));
            newWords.setWords(rs.getString("word"));

            words.add(newWords);

        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return words;
    }

    public ArrayList<String> getAll() {
        String sql = "SELECT * FROM Words ";
        ArrayList<String> words = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ){
            while(rs.next()) {
                words.add(rs.getString("word"));
            }
        } catch (Exception e) {

        }

        return words;
    }

    public void insert(String longUrl, String shortUrl){
        createTable();
        String sql = "INSERT INTO LinkList(long,short) VALUES(?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, longUrl);
            pstmt.setString(2, shortUrl);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

