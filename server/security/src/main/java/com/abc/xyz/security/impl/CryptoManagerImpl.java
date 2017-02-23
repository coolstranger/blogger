package com.abc.xyz.security.impl;

import com.abc.xyz.security.CryptoManager;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Component
public class CryptoManagerImpl implements CryptoManager {

    @Override
    public byte[] encodePassword(byte[] password, byte[] salt, String alg) {
        int length = Integer.min(password.length, salt.length);
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for(int i=0; i<length; i++){
                out.write(salt[i]);
                out.write(password[i]);
            }
            byte[] hash = getHash(out.toByteArray(), alg);
            return hash;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public byte[] getSalt(int len){
        SecureRandom sr = new SecureRandom();
        byte[] b = new byte[len];
        sr.nextBytes(b);
        return b;
    }

    @Override
    public String getRandomGUID(){
        String ret = "";
        char []c = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        SecureRandom sr = new SecureRandom();
        for(int i=0;i<36;i++){
            int index = Math.abs(sr.nextInt()%16);
            ret+=c[index];
        }
        return ret;

    }

    @Override
    public byte[] getBytes(int len){
        SecureRandom sr = new SecureRandom();
        byte[] b = new byte[len];
        sr.nextBytes(b);
        return b;
    }

    @Override
    public String base64Encode(byte[] encode){
        return Base64.encodeBase64String(encode);
    }

    @Override
    public byte[] base64Decode(String encoded){
        return Base64.decodeBase64(encoded);
    }

    @Override
    public byte[] getHash(byte[] data, String alg){
        try {
            MessageDigest md = MessageDigest.getInstance(alg);
            md.reset();
            md.update(data);
            byte[] digest = md.digest();
            return digest;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
