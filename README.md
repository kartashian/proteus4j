##Factory Method pattern Java implementation library

###Usage
Basically, to use proteus4j first marked super class with ```@Parent``` and its subclass with ```@Child```

```java
@Parent
public interface Figure {
    // parent super class
}

@Child(name = Shape.Value.SQUARE, singleton = true)
public class Square implements Figure {
    //child class
}
```

Recommended use enum for child class naming for avoiding syntax errors

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

Then use ```Factory``` class for getting parent type instances
```java
public class FigureService {
    
    public void draw(Shape shape) {
        Figure figure = Factory.get(Figure.class, shape.getName());
        ...
    }
}
```
###Licence
**proteus4j** library strictly protected by [WTFPL](http://www.wtfpl.net/), so just do what the fuck you want to. 
