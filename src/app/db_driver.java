package app;

import java.sql.*;
import java.util.ArrayList;

public class db_driver {
    private static Statement stmt;
    private static Connection conn = null;   // For MySQL only
    private static ArrayList<Book> all_books = new ArrayList<>();
    public static boolean AdminMode;


    static void loadBooks() {

        // Step 3: Execute a SQL SELECT query. The query result is returned in a 'ResultSet' object.
        String strSelect = "select * from book_info";
        System.out.println("The SQL statement is: " + strSelect + "\n"); // Echo For debugging

        ResultSet rset = null;
        try {
            rset = stmt.executeQuery(strSelect);

            // Step 4: Process the ResultSet by scrolling the cursor forward via next().
            System.out.println("The records selected are:");
//            int rowCount = 0;
            while (rset.next()) {   // Move the cursor to the next row, return false if no more row

                String ISBN = rset.getString("ISBN");
                String name = rset.getString("name");
                String author = rset.getString("author");
                String desc = rset.getString("description");
                Date date = rset.getDate("publishing_date");
                // TODO add price field

                all_books.add(new Book(ISBN, name, author, desc, date));
                System.out.println(ISBN + "\t" + name + "\t" + author + "\t" + date + "\nDescription:\n" + desc + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    static boolean connectToUser(String user, String pass) {

        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                    user, pass);

            // Step 2: Allocate a 'Statement' object in the Connection

            stmt = conn.createStatement();
            System.out.println("Logged in user: " + user);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static void insertValues(String ISBN, String name, String author, String desc, Date date, Double price) {
        System.out.println(ISBN);
        int i = 0;
        try {
            // TODO allow to add null values
            PreparedStatement statement = conn.prepareStatement("INSERT INTO book_info(ISBN, name, author, description, publishing_date) VALUES(?, ?, ?, ?, ?)");
            statement.setString(1, ISBN);
            statement.setString(2, name);
            statement.setString(3, author);
            statement.setString(4, desc);
            statement.setDate(5, date);

            i = statement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(i + "executed");
    }


    static ArrayList<Book> getAll_books() {
        return all_books;
    }


}
