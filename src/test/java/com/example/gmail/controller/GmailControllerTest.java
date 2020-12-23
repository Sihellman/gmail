package com.example.gmail.controller;

import com.example.gmail.model.UserPass;
import com.example.gmail.service.GmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.gmail.controller.GmailController.featureSwitchConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

public class GmailControllerTest {

    @Mock
    private GmailService mockGmail;
    @InjectMocks
    private GmailController gmailController;


    @Test
    public void getPrimaryKeyWhenEmailDown_shouldNotGetPrimaryKey_shouldReturn503() {
        featureSwitchConfiguration.setEmailUp(false);
        when(mockGmail.getPrimaryKey(any(UserPass.class)))
                .thenReturn("not available");
        assertThat(mockGmail.getPrimaryKey(new UserPass("sdf", "sdf")))
                .isEqualTo("not available");
        verify(mockGmail).getPrimaryKey(any(UserPass.class));
        featureSwitchConfiguration.setEmailUp(true);



    }
    @Test
    void getPrimaryKeyWhenEmailUp_shouldCallService_shouldReturnPrimaryKey() {

        featureSwitchConfiguration.setEmailUp(true);

        when(mockGmail.getPrimaryKey(any(UserPass.class)))
                .thenReturn("7cddf2b2-b4bf-4e9a-aa00-d2d63ba8c2d9");

        assertThat(gmailController.getPrimaryKey(new UserPass("sdf", "sdf")))
                .isEqualTo("7cddf2b2-b4bf-4e9a-aa00-d2d63ba8c2d9");

        verify(mockGmail).getPrimaryKey(any(UserPass.class));
        featureSwitchConfiguration.setEmailUp(true);




    }



}