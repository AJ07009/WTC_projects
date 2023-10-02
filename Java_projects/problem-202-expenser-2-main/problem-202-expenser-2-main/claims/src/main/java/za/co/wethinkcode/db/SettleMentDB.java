package za.co.wethinkcode.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.UUID;

public class SettleMentDB implements ExpenseDB{
        private static final String CONN = "jdbc:sqlite:SettleDB.db";

        public static Connection connect() {
            Connection conn = null;

            if (isSuitableDriverAvailable()) {
                try {
                    conn = DriverManager.getConnection(CONN);
                } catch (Exception e) {
                }
            } else {
                System.err.println("The driver was not correctly loaded.");
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

        @Override
        public void createDB() {
            String create = "CREATE TABLE IF NOT EXISTS Expenses (\n"
                    + " uid text,\n"
                    + " settleDate text\n"
                    + ");";
            try (Connection conn = connect();
                 Statement stmt = conn.createStatement()){
                stmt.execute(create);

            } catch (Exception e) {
                //TODO: handle exception Like a pro
            }
        }

        @Override
        public UUID getId() {
            return null;
        }
}
