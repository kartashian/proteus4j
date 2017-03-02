package org.proteus4j.builder.model;

import org.proteus4j.builder.base.Builder;

public class Book {

    private String name;
    private String author;
    private String color;

    private Book() {
    }

    public static Builder<Book> newBuilder() {
        return Builder.of(Book::new);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
