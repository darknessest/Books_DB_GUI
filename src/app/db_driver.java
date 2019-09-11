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
//        String strSelect = "select I.ISBN, I.name, I.author, I.description, I.publishing_date, S.price " +
//                "from book_info as I, book_stock as S where S.ISBN=I.ISBN order by name";
        String strSelect = "call getAllBooksInfo()";
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
                double price = rset.getDouble("price");
                String desc = rset.getString("description");
                Date date = rset.getDate("publishing_date");

                all_books.add(new Book(ISBN, name, author, price, desc, date));
//                System.out.println(ISBN + "\t" + name + "\t" + author + "\t" + date + "\nDescription:\n" + desc + "\n");
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

    static int insertRecord(String ISBN, String name, String author, String desc, Date date, Double price) {
        System.out.println("inserting " + ISBN);
        int i = 0;
        try {
            // TODO allow to add null values
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO book_info(ISBN, name, author, description, publishing_date) " +
                            "VALUES(?, ?, ?, ?, ?)");
            statement.setString(1, ISBN);
            statement.setString(2, name);
            statement.setString(3, author);
            statement.setString(4, desc);
            statement.setDate(5, date);

            i = statement.executeUpdate();

            // trigger has already inserted new data, so just need to update one cell
            statement = conn.prepareStatement("update book_stock SET price = ? " +
                    "where ISBN = ?");
            statement.setDouble(1, price);
            statement.setString(2, ISBN);
            i = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    static int updateRecord(String ISBN, String name, String author, String desc, Date date, Double price) {
        System.out.println("updating " + ISBN);
        int i = 0;
        try {
            // TODO allow to add null values
            PreparedStatement statement = conn.prepareStatement("UPDATE book_info SET " +
                    "name = ?, author = ?, description = ?, publishing_date = ? " +
                    "WHERE ISBN = ?");
            statement.setString(1, name);
            statement.setString(2, author);
            statement.setString(3, desc);
            statement.setDate(4, date);
            statement.setString(5, ISBN);

            //TODO update price table too
            i = statement.executeUpdate();

            statement = conn.prepareStatement("update book_stock SET price = ? " +
                    "where ISBN = ?");
            statement.setDouble(1, price);
            statement.setString(2, ISBN);

            i = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    static void deleteRecord(String ISBN) {
        System.out.println("deleting " + ISBN);
        try {
            // TODO allow to add null values
            PreparedStatement statement = conn.prepareStatement("DELETE FROM book_info " +
                    "WHERE ISBN = ?");
            statement.setString(1, ISBN);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void buyBook(String ISBN) {
        try {
            PreparedStatement statement = conn.prepareStatement("update book_stock SET qty_in = qty_in - 1,  qty_sold = qty_sold + 1 " +
                    "WHERE ISBN = ?");
            statement.setString(1, ISBN);
            System.out.println("buying " + ISBN);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static boolean checkBookAval(String ISBN) {
        try {
            PreparedStatement statement = conn.prepareStatement("select qty_in from book_stock " +
                    "WHERE ISBN = ?");
            statement.setString(1, ISBN);
            System.out.println("checking " + ISBN);
            ResultSet rset = statement.executeQuery();
            if (rset.next())
                return rset.getInt("qty_in") > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static ArrayList<Book> getAll_books() {
        return all_books;
    }

}
