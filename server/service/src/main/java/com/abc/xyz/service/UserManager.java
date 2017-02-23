package com.abc.xyz.service;


import com.abc.xyz.common.data.User;

public interface UserManager {

    void createUser(User user, String password);
    void updateUser(User user);
    void changePassword(String oldPassword, String newPassword, String userId);
    boolean verifyCredential(String userId, String password);
    public void updateUserLoginTime(String userId);
    User getUserFromLoginUid(String loginUid);
    User getUser(String userId);

}
