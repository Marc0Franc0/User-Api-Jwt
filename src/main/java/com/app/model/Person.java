package com.app.model;

import lombok.Data;

@Data
public abstract class Person {
    private String firstName;
    private String lastName;
    private int age;
    private String email;
}
