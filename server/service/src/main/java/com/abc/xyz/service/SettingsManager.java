package com.abc.xyz.service;


/**
 * This Service is used to fetch settings local and global for User.
 */
public interface SettingsManager {

    int getBatchSize(String userId);
    int getSearchSize(String userId);
    int getGlobalSearchSize();
}
