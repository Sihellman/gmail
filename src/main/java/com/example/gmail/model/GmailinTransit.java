package com.example.gmail.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@ApiModel()

public class GmailinTransit {
    @ApiModelProperty("if the sender is from this server, the from will be a uuid. Otherwise, the from will be" +
            " a username")
    public String from;
    @ApiModelProperty("the users do not need to know the uuid of the recipient, so the username is sufficient")
    public String recipientUsername;
    public String message;


}