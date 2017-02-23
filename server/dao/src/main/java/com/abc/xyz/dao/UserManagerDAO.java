package com.abc.xyz.dao;

import com.abc.xyz.schema.CredentialEntity;
import com.abc.xyz.schema.UserEntity;

public interface UserManagerDAO extends BaseDAO{


    UserEntity getUserFromLoginUid(String loginUid);
    CredentialEntity getCredential(String userId);

}

