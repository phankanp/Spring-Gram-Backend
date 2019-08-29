package com.phan.spring_gram_backend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AliasAlreadyExistsResponse {

    private String alias;

    public AliasAlreadyExistsResponse(String alias) {
        this.alias = alias;
    }
}
