##Software design patterns Java implementation library

##Implemented pattern list:
- [Factory Method](#factory-method-pattern)
- [Builder](#builder-pattern)

##Usage
###Factory Method Pattern
Basically, to implement factory method, first marked super class with ```@Parent``` and its subclass with ```@Child```
or declare methods under ```@ProteusConfiguration``` classx 

```java
@Parent
public interface Figure {
    // parent super class
}

@Child(value = Shape.Value.SQUARE, singleton = true)
public class Square implements Figure {
    //child class
}
```

Recommended to use enum for child class naming to avoid syntax errors

```java
public enum Shape {
    SQUARE(Value.SQUARE);
    
    private String name;
    
    Shape(String name) {
         this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public static class Value {
       public static final String SQUARE = "SQUARE";
    }
}
```

And then, just use ```Factory``` class for getting parent type instances
```java
public class FigureService {
    
    public void draw(Shape shape) {
        Figure figure = Factory.get(Figure.class, shape.getName());
        ...
    }
}
```
###Builder Pattern
```Builder``` implementation can help create an instance of any object without the need to write builder boilerplate code.
Just specify class with setters, like this:

```java
public class Book {    
    ...

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
```
Or make safe inner builder inside of the class:
```java
public class Book {
    
    private Book() {
    }
    
    public static Builder<Book> newBuilder() {
        return Builder.of(Book::new);
    }
    
    ...
}
```
And call builder like this:
```java
public class BookService {
    
    public Book print(String name, String author, String color) {
        Book book = Builder.of(Book::new)
                        .with(Book::setName, name)
                        .with(Book::setAuthor, author)
                        .with(Book::setColor, color)
                        .build();
        ...
    }
}
```
##Licence
**proteus4j** library strictly protected by [WTFPL](http://www.wtfpl.net/), so just do what the fuck you want to. 
