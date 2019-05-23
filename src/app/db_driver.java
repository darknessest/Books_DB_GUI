package app;

import java.sql.*;

public class db_driver {
    private static Statement stmt;

    public static void showBooks() {

        // Step 3: Execute a SQL SELECT query. The query result is returned in a 'ResultSet' object.
        String strSelect = "select * from book_info";
        System.out.println("The SQL statement is: " + strSelect + "\n"); // Echo For debugging

        ResultSet rset = null;
        try {
            rset = stmt.executeQuery(strSelect);

            // Step 4: Process the ResultSet by scrolling the cursor forward via next().
            System.out.println("The records selected are:");
            int rowCount = 0;
            while (rset.next()) {   // Move the cursor to the next row, return false if no more row
                String ISBN = rset.getString("ISBN");
                String name = rset.getString("name");
                String author = rset.getString("author");
                String desc = rset.getString("description");
                Date date = rset.getDate("publishing_date");
                System.out.println(ISBN + "\t" + name + "\t" + author + "\t" + date + "\nDescription:\n" + desc + "\n");
                ++rowCount;
            }

            System.out.println("Total number of records = " + rowCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean connectToUser(String user, String pass) {
        Connection conn = null;   // For MySQL only
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    user, pass);


            // Step 2: Allocate a 'Statement' object in the Connection

            stmt = conn.createStatement();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
