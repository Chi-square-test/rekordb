package com.rekordb.rekordb.security.Exception;

public class TokenValidFailedException extends RuntimeException{
    public TokenValidFailedException() {
        super("Failed to generate Token.");
    }

    private TokenValidFailedException(String message) {
        super(message);
    }
}
