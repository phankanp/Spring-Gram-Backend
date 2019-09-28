package com.phan.spring_gram_backend.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String JWT_SECRET = "57577628f61441aba8298ecf93831d41";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 12_000_000;
}
