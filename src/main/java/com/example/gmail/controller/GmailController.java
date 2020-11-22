package com.example.gmail.controller;

import com.example.gmail.config.ExternalMailConfiguration;
import com.example.gmail.config.FeatureSwitchConfiguration;
import com.example.gmail.model.GmailinTransit;
import com.example.gmail.model.Key;
import com.example.gmail.service.GmailService;
import com.example.gmail.service.UserPass;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@AllArgsConstructor


@RestController
@RequestMapping("/api/v1/email")
public class GmailController {
    private final GmailService gmailService;
    private final ExternalMailConfiguration externalMailConfiguration;
    private final FeatureSwitchConfiguration featureSwitchConfiguration;
    @PostMapping("/login")
    public Object getPrimaryKey(@RequestBody UserPass userPass) {
        if (featureSwitchConfiguration.isEmailUp()){
            //System.out.println("complex stuff" + externalMailConfiguration.getIp());
            return gmailService.getPrimaryKey(userPass);
        }
        return new ResponseEntity<>("not available", HttpStatus.SERVICE_UNAVAILABLE);

    }
    @PostMapping("/receiveExternalMail")
    public Object receiveExternalMail(@RequestBody GmailinTransit gmailinTransit, @RequestHeader("api-key") String key){
        return gmailService.receiveExternalMail(gmailinTransit);
    }
    @PostMapping("/send")
    public Object send(@RequestBody GmailinTransit gmailinTransit){
        return gmailService.send(gmailinTransit);
    }

    @PostMapping("/inbox")
    public Object inbox(@RequestBody Key key){
        return gmailService.inbox(key);
    }

    @PostMapping("/outbox")
    public Object outbox(@RequestBody Key key){
        return gmailService.outbox(key);
    }

}
