package com.phan.spring_gram_backend.security.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class JWTResponse {

    private boolean success;

    private String token;

    public boolean isSuccess() {
        return success;
    }
}
