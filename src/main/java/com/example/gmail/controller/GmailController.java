package com.example.gmail.controller;

import com.example.gmail.config.ExternalMailConfiguration;
import com.example.gmail.config.FeatureSwitchConfiguration;
import com.example.gmail.model.GmailinTransit;
import com.example.gmail.model.Key;
import com.example.gmail.model.StringObject;
import com.example.gmail.service.GmailService;
import com.example.gmail.service.UserPass;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
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
    @PostMapping("/sendString")
    public Object sendString(@RequestBody StringObject string){
        return gmailService.sendString(string);
    }
    @PostMapping("/receiveString")
    public Object receiveString(@RequestBody StringObject string, @RequestHeader( value = "api-key") String key) throws UnsupportedEncodingException{
        if(Base64.getEncoder().encodeToString(
                "letMeIn".getBytes("utf-8")).equals(key) == false){
            return new ResponseEntity<>( "wrong code", HttpStatus.UNAUTHORIZED);
        }
        return gmailService.receiveString(string);

    }
    @GetMapping("/showString")
    public Object showString(){
        return gmailService.showString();
    }
    @PostMapping("/receiveExternalMail")
    public Object receiveExternalMail(@RequestBody GmailinTransit gmailinTransit, @RequestHeader( value = "api-key") String key) throws UnsupportedEncodingException {
        if (featureSwitchConfiguration.isEmailUp()){
            if(Base64.getEncoder().encodeToString(
                    "letMeIn".getBytes("utf-8")).equals(key) == false){
                return new ResponseEntity<>( "wrong code", HttpStatus.UNAUTHORIZED);
            }
            return gmailService.receiveExternalMail(gmailinTransit);
        }
        return new ResponseEntity<>("not available", HttpStatus.SERVICE_UNAVAILABLE);

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
