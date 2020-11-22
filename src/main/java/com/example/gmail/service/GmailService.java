package com.example.gmail.service;

import com.example.gmail.model.*;
import com.example.gmail.utils.User;
import lombok.RequiredArgsConstructor;
import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service

public class GmailService {
    private final RestTemplate restTemplate;
    public  GmailService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    public Object getPrimaryKey(UserPass userPass){//should not need loop
        //UserPass userPassSivia = new UserPass("sivia", "hippo");
        //User.map.put(userPassSivia, UUID.randomUUID());
        /*if(User.map.containsKey(userPass)){
            return new ResponseEntity<>( User.map.get(userPass).toString(), HttpStatus.OK);
        }*/

        for(UUID key : User.map.keySet()){
            if(userPass.equals(User.map.get(key))){
                return new ResponseEntity<>( key.toString(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("better luck next time  ", HttpStatus.UNAUTHORIZED);
        //return userPass.toString();
        //return User.map.toString();
    }
    public Object receiveExternalMail(GmailinTransit gmailinTransit){


    }

    public Object send(GmailinTransit gmailinTransit){//avoided a loop, had to use a loop
       try{
           UUID uuid = UUID.fromString(gmailinTransit.getFrom());
           if(User.map.containsKey(uuid) == false){
               return new ResponseEntity<>("invalid UUID", HttpStatus.UNPROCESSABLE_ENTITY);
           }
           for(UUID key : User.map.keySet()){
               if(gmailinTransit.getRecipientUsername().equals(User.map.get(key).getUsername())){
                   Gmail gmail = new Gmail(key, uuid, gmailinTransit.getMessage());
                   User.GMAILS.add(gmail);
                   return new ResponseEntity<>( "message sent", HttpStatus.OK);
               }

           }
       }
       catch (IllegalArgumentException e){
           return new ResponseEntity<>("invalid UUID", HttpStatus.UNPROCESSABLE_ENTITY);
       }

        return new ResponseEntity<>("nonexistent username", HttpStatus.NOT_FOUND);
    }
    public Object inbox(Key key){//avoided a loop
        UUID user = UUID.fromString(key.getKey());
        if(User.map.containsKey(user) == false){
            return new ResponseEntity<>("invalid UUID", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<InboxView> inboxContents = new LinkedList<>();

        for(int i = 0; i < User.GMAILS.size(); i++){
            if(User.GMAILS.get(i).getTo().equals(user)){
                String username = User.map.get(User.GMAILS.get(i).getFrom()).getUsername();
                InboxView inboxView = new InboxView(username, User.GMAILS.get(i).getMessage());
                inboxContents.add(inboxView);
            }
        }
        return new ResponseEntity<>(inboxContents, HttpStatus.OK);
    }

    public Object outbox(Key key){//avoided a loop
        UUID user = UUID.fromString(key.getKey());
        if(User.map.containsKey(user) == false){
            return new ResponseEntity<>("invalid UUID", HttpStatus.UNPROCESSABLE_ENTITY);
        }
        List<OutboxView> outboxContents = new LinkedList<>();

        for(int i = 0; i < User.GMAILS.size(); i++){
            if(User.GMAILS.get(i).getFrom().equals(user)){
                String username = User.map.get(User.GMAILS.get(i).getTo()).getUsername();
                OutboxView outboxView = new OutboxView(username, User.GMAILS.get(i).getMessage());
                outboxContents.add(outboxView);
            }
        }
        return new ResponseEntity<>(outboxContents, HttpStatus.OK);
    }

}
