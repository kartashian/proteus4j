package org.proteus4j.builder;

import org.junit.Test;
import org.proteus4j.builder.base.Builder;
import org.proteus4j.builder.model.Book;
import org.proteus4j.builder.model.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BuilderTest {

    private static final String BOOK_NAME = "Harry Potter";
    private static final String AUTHOR = "J.K. Rowling";
    private static final Integer AGE = 44;
    private static final String COLOR = "White";

    @Test
    public void build() {
        Person person = Builder.of(Person::new)
                .with(Person::setName, AUTHOR)
                .with(Person::setAge, AGE)
                .with(Person::setColor, COLOR)
                .build();

        assertNotNull(person);
        assertEquals(person.getName(), AUTHOR);
        assertEquals(person.getAge(), AGE);
        assertEquals(person.getColor(), COLOR);
        System.out.println(person);
    }

    @Test
    public void privateBuild() {
        Book book = Book.newBuilder()
                .with(Book::setName, BOOK_NAME)
                .with(Book::setAuthor, AUTHOR)
                .with(Book::setColor, COLOR)
                .build();

        assertNotNull(book);
        assertEquals(book.getName(), BOOK_NAME);
        assertEquals(book.getAuthor(), AUTHOR);
        assertEquals(book.getColor(), COLOR);
        System.out.println(book);
    }
}
