package com.sdlab.sdlab.util;

import org.springframework.stereotype.Service;

@Service
public class TokenGeneratorImpl implements TokenGenerator {

    private static final String ALPHA_NUMERIC_STRING =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public String generateToken() {
        //generates a 128 character long token
        int count = 128;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
