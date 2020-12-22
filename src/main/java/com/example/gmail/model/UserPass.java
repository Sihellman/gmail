package com.example.gmail.model;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
@ApiModel(description = "There can be multiple users with the same username." +
        " Each userPass is stored with a uuid, in a hashmap.")
public class UserPass {

    public String username;

    public String password;


}
