package com.example.gmail.model;

import com.example.gmail.service.UserPass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data


public class GmailinTransit {
    public String from;
    public String recipientUsername;
    public String message;


}