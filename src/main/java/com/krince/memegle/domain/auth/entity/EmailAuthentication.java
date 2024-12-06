package com.krince.memegle.domain.auth.entity;

import com.krince.memegle.global.constant.AuthenticationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import static lombok.AccessLevel.*;

@Getter
@Builder
@RedisHash(value = "emailAuthentication", timeToLive = 300)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class EmailAuthentication {

    @Id
    private String id;
    private String email;
    private String userName;
    private String authenticationCode;
    private AuthenticationType authenticationType;
}
