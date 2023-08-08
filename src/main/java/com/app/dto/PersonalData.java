package com.app.dto;

import jakarta.persistence.Embeddable;

@Embeddable
public record PersonalData(String firstName,
                           String lastName,
                           int age,
                           String email) {

}
