package com.sdlab.sdlab.util;

import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PasswordEncrypterSHA512 implements PasswordEncrypter {

    private MessageDigest messageDigest;

    public PasswordEncrypterSHA512() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String password) {
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("String to encrypt cannot be null or have zero length!");
        }
        System.out.println("Message digest is : " + messageDigest);
        messageDigest.update(password.getBytes());
        byte[] hash = messageDigest.digest();
        StringBuffer hexString = new StringBuffer();
        String myHash = DatatypeConverter.printHexBinary(hash).toUpperCase();
        return myHash;

    }
}
