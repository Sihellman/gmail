package com.example.gmail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.UUID;
@Data
@ApiModel
public class Key {
    @ApiModelProperty(value="The key is a uuid converted to a string. It is a parameter for the inbox and " +
            " outbox because the user needs to input their own uuid in order to see their inbox or " +
            " outbox. The key is converted back to a uuid to see if the uuid exists in the database of users.",
    example = "1234-1234-12345678")
    public String key;
    public Key(){
        key = UUID.randomUUID().toString();
    }
}
