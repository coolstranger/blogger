package com.abc.xyz.service;


public interface ConfigManager {

    String getDigestAlgorithm();
    String getHashingAlgorithm();
    long getExpiryTime();

}
