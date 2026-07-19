package com.spring.ai.helper;

import java.util.List;

public class Helper {

    public static List<String> getData() {

        return List.of(
                "Java is a high-level, object-oriented programming language developed by Sun Microsystems.",
                "The JVM allows Java applications to run on any operating system without recompilation.",
                "JDK stands for Java Development Kit and contains tools required for Java development.",
                "JRE provides the runtime environment needed to execute Java applications.",
                "Classes and objects are the fundamental building blocks of object-oriented programming in Java.",
                "Inheritance allows one class to acquire the properties and methods of another class.",
                "Polymorphism enables the same method to behave differently based on the object invoking it.",
                "Encapsulation protects data by restricting direct access through private fields and public methods.",
                "Abstraction hides implementation details and exposes only essential functionality to the user.",
                "Exception handling in Java is implemented using try, catch, finally, throw, and throws keywords.",
                "The Collection Framework provides interfaces and classes such as List, Set, Map, and Queue.",
                "Streams API introduced in Java 8 simplifies processing collections using functional programming concepts.",
                "Lambda expressions provide a concise way to implement functional interfaces in Java.",
                "Spring Boot simplifies Java application development by providing auto-configuration and embedded servers.",
                "Spring Data JPA reduces boilerplate code for database access using repository interfaces.",
                "Hibernate is a popular ORM framework that maps Java objects to relational database tables.",
                "Multithreading allows multiple threads to execute concurrently, improving application performance.",
                "Garbage Collection automatically manages memory by removing objects that are no longer referenced.",
                "REST APIs in Java are commonly developed using Spring Boot with @RestController annotations.",
                "Java remains one of the most widely used programming languages for enterprise application development."
        );
    }
}