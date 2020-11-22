package com.example.gmail.model;

import lombok.Data;

import java.util.UUID;
@Data
public class Key {
    public String key;
    public Key(){
        key = UUID.randomUUID().toString();
    }
}
