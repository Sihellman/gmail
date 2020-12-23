package com.example.gmail.controller;

import com.example.gmail.config.ExternalMailConfiguration;
import com.example.gmail.config.FeatureSwitchConfiguration;
import com.example.gmail.model.GmailinTransit;
import com.example.gmail.model.Key;
import com.example.gmail.model.UserPass;
import com.example.gmail.service.GmailService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@AllArgsConstructor


@RestController
@Api(value = "Gmail", description = "methods", tags = { "Gmail" })

@RequestMapping("/api/v1/email")
public class GmailController {
    private final GmailService gmailService;
    private final ExternalMailConfiguration externalMailConfiguration;
    public final static FeatureSwitchConfiguration featureSwitchConfiguration = new FeatureSwitchConfiguration();
    @ApiOperation(notes="log in and receive uuid", value = "login")
    /*@ApiResponses(value = {@ApiResponse(code = 401, message = "nonexistent username"),
    //@ApiResponses(value = {@ApiResponse(code = 200, message = "ok", response = String.class,         examples = @Example(value = {@ExampleProperty(mediaType = "application/json", ))}),


            @ApiResponse(code = 503, message = "unavailable")})*/


  @ApiResponses(value = {@ApiResponse(code = 200, message = "ok", response = Object.class),
          @ApiResponse(code = 503, message = "unavailable"),
          @ApiResponse(code = 401, message = "nonexistent username")})

    @PostMapping("/login")
    public Object getPrimaryKey(@ApiParam(value = "As of now, there can be multiple users with the same username. " +
            " Each userpass is stored with a uuid, in a hashmap.") @RequestBody UserPass userPass ) {
      //featureSwitchConfiguration.setEmailUp(true);

          if ((featureSwitchConfiguration.isEmailUp()) ){
              //System.out.println("complex stuff" + externalMailConfiguration.getIp());
              return gmailService.getPrimaryKey(userPass);
          }
          else{
              return new ResponseEntity<>("unavailable", HttpStatus.SERVICE_UNAVAILABLE);
          }




    }


    /*@PostMapping("/sendString")
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
    }*/
    @ApiResponses(value = {@ApiResponse(code = 200, message = "you got mail", response = Object.class),
            @ApiResponse(code = 503, message = "unavailable"),
            @ApiResponse(code = 404, message = "no such username"),
            @ApiResponse(code = 401, message = "wrong header")})
    @PostMapping("/receiveExternalMail")
    public Object receiveExternalMail(@ApiParam("If the sender is not external, the from string " +
            " will be a uuid, otherwise the from will be a username. Posting to receiveExternalMail " +
            " from this server should only be for testing purposes because the sender will show up in " +
            " the inbox as a uuid.") @RequestBody GmailinTransit gmailinTransit,
                                      @ApiParam(value = "Base 64 encoded 'letMeIn' as the value. The key is api-key.")
    @RequestHeader( value = "api-key") String key) throws UnsupportedEncodingException {
        if (featureSwitchConfiguration != null){
            if (featureSwitchConfiguration.isEmailUp()){
                if(!Base64.getEncoder().encodeToString(
                        "letMeIn".getBytes(StandardCharsets.UTF_8)).equals(key)){
                    return new ResponseEntity<>( "wrong code", HttpStatus.UNAUTHORIZED);
                }
                return gmailService.receiveExternalMail(gmailinTransit);
            }
        }
        return new ResponseEntity<>("not available", HttpStatus.SERVICE_UNAVAILABLE);


    }
    @ApiOperation(value = "send", notes = "send mail internally and externally")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "message sent", response = Object.class),
            @ApiResponse(code = 400, message = "error to do with restTemplate"),
            @ApiResponse(code = 401, message = "invalid uuid")})
    @PostMapping("/send")
    public Object send(@ApiParam("The from string is a uuid. if the recipient is not found " +
            " in this server, the mail is sent to an external server.")
                           @RequestBody GmailinTransit gmailinTransit){
        return gmailService.send(gmailinTransit);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok",  response = Object.class),
            @ApiResponse(code = 401, message = "invalid uuid")})
    @PostMapping("/inbox")
    public Object inbox(@ApiParam("String to be converted to uuid in order to access inbox")
                            @RequestBody Key key){
        return gmailService.inbox(key);
    }

    @ApiResponses(value = {@ApiResponse(code = 200, message = "ok",  response = Object.class),
            @ApiResponse(code = 401, message = "invalid uuid")})
    @PostMapping("/outbox")
    public Object outbox(@ApiParam("String to be converted to uuid in order to access outbox")
                             @RequestBody Key key){
        return gmailService.outbox(key);
    }

}
