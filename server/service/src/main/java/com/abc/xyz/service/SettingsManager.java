package com.abc.xyz.service;


public interface SettingsManager {

    int getBatchSize(String userId);
    int getSearchSize(String userId);
    int getGlobalSearchSize();
}
