package app;

import java.sql.Date;

public class Book {
    private String ISBN;
    private String name;
    private String author;
    private String description;
    private Date date;
    private double price;

    public Book(String ISBN, String name, String author, Double price, String description, Date date) {
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.price = price;
        this.description = description;
        this.date = date;
    }

    public Book() {
        this.ISBN = "";
        this.name = "";
        this.author = "";
        this.description = "";
        this.date = new Date(0, 0, 1);
    }

    Book(String name) {
        this.name = name;
    }

    String getISBN() {
        return ISBN;
    }

    String getName() {
        return name;
    }

    String getAuthor() {
        return author;
    }

    String getDescription() {
        return description;
    }

    Date getDate() {
        return date;
    }

    double getPrice() {
        return price;
    }

    void setValues(String ISBN, String name, String author, Double price, String description, Date date) {
        this.ISBN = ISBN;
        this.name = name;
        this.author = author;
        this.price = price;
        this.description = description;
        this.date = date;

    }
}
