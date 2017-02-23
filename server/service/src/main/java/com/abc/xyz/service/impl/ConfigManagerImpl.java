package com.abc.xyz.service.impl;


import com.abc.xyz.service.ConfigManager;
import org.springframework.stereotype.Component;

@Component
public class ConfigManagerImpl implements ConfigManager {
    @Override
    public String getDigestAlgorithm() {
        return "SHA1";
    }

    @Override
    public String getHashingAlgorithm() {
        return "SHA1";
    }

    @Override
    public long getExpiryTime() {
        return 3600000;
    }
}
