package com.abc.xyz.security;

/**
 * Service for operations like base64 enc/dec , password hashing
 */
public interface CryptoManager {

    public byte[] getSalt(int len);
    public byte[] encodePassword(byte[] password, byte[] salt, String alg);
    public byte[] getBytes(int len);
    public String getRandomGUID();
    public String base64Encode(byte[] encode);
    public byte[] base64Decode(String encoded);
    byte[] getHash(byte[] data, String alg);

}
