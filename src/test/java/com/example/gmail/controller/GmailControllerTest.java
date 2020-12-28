/*
package com.example.gmail.controller;

import com.example.gmail.config.FeatureSwitchConfiguration;
import com.example.gmail.model.Key;
import com.example.gmail.model.UserPass;
import com.example.gmail.service.GmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;


@ExtendWith(MockitoExtension.class)

public class GmailControllerTest {
    @Mock
    private GmailService emailService;

    @Mock
    private FeatureSwitchConfiguration featureSwitchConfiguration;

    @InjectMocks
    private GmailController subject;

    @Test
    public void login_returnsSuccess_whenEmailIsTurnedOn_andLoginIsSuccessful() throws Exception {
        Key key = new Key();

        UserPass userPass = UserPass.builder().build();

        when(featureSwitchConfiguration.isEmailUp()).thenReturn(true);
        when(emailService.getPrimaryKey(any(UserPass.class))).thenReturn(key);

        Object actual = subject.getPrimaryKey( userPass);

        verify(featureSwitchConfiguration).isEmailUp();
        verify(emailService).getPrimaryKey(userPass);

        assertThat(((ResponseEntity)actual).getStatusCode()).isEqualTo(OK);
        assertThat(actual).isEqualTo(key);
    }

    @Test
    public void login_returnsServerDown_whenEmailIsTurnedOff() throws Exception {
        when(featureSwitchConfiguration.isEmailUp()).thenReturn(false);

        Object actual = subject.getPrimaryKey(UserPass.builder().build());

        verify(featureSwitchConfiguration).isEmailUp();

        assertThat(((ResponseEntity)actual).getStatusCode()).isEqualTo(SERVICE_UNAVAILABLE);
        assertThat((String)((ResponseEntity)actual).getBody()).isEqualTo("Sorry, our server is down right now");
    }



}*/
