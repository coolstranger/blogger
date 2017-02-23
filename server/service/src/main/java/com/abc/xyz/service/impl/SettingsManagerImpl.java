package com.abc.xyz.service.impl;

import com.abc.xyz.service.SettingsManager;
import org.springframework.stereotype.Component;


@Component
public class SettingsManagerImpl implements SettingsManager {

    @Override
    public int getBatchSize(String userId) {
        return 5;
    }

    @Override
    public int getSearchSize(String userId) {
        return 5;
    }

    @Override
    public int getGlobalSearchSize() {
        return 5;
    }
}
