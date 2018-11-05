package com.dawid.sternik.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Recipe {

    private @Id
    @GeneratedValue
    Long id;
    private String name;
    private String type;

    public Recipe(){}

    public Recipe(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
