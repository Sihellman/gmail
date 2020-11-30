package com.example.gmail.utils;

import com.example.gmail.model.ExternalGmail;
import com.example.gmail.model.Gmail;
import com.example.gmail.service.UserPass;
import lombok.Data;
import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@UtilityClass
public class User {

    //public final static Map<UserPass, UUID> map = new HashMap<UserPass, UUID>();
    public final static Map<UUID, UserPass> map = Stream.of(
            new AbstractMap.SimpleEntry<>(UUID.randomUUID(), UserPass.builder()
                    .password("pass")
                    .username("jeff")
                    .build()  ),
            new AbstractMap.SimpleEntry<>(UUID.randomUUID(), UserPass.builder()
                    .password("pass")
                    .username("sivia")
                    .build()   )
            )
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));



    public final static List<Gmail> GMAILS =  new ArrayList<>();
    public final static List<ExternalGmail> EXTERNAL_GMAILS =  new ArrayList<>();

}
