package app;

import java.sql.Date;

public class Book {
    private String ISBN;
    private String name;
    private String author;
    private String description;
    private Date date;

    public Book(String ISBN, String name, String author, String description, Date date) {
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.description = description;
        this.date = date;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }
}
