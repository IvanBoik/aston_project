package com.aston.aston_project.util;

import com.google.common.hash.Hashing;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static java.awt.SystemColor.text;

public class PasswordUtils {


    public static String encode(String password){
        return Hashing.sha256().hashBytes(password.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public static boolean compare(String realPassword,String toCompare){
        String encoded = Hashing.sha256().hashBytes(toCompare.getBytes(StandardCharsets.UTF_8)).toString();
        return realPassword.equals(encoded);
    }
}
