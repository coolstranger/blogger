package com.abc.xyz.service.impl;


import com.abc.xyz.common.*;
import com.abc.xyz.common.data.User;
import com.abc.xyz.dao.UserManagerDAO;
import com.abc.xyz.schema.CredentialEntity;
import com.abc.xyz.schema.UserEntity;
import com.abc.xyz.security.CryptoManager;
import com.abc.xyz.service.ConfigManager;
import com.abc.xyz.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(value= Constants.TRANSACTION_MANAGER, propagation = Propagation.REQUIRED)
public class UserManagerImpl implements UserManager {

    @Autowired
    private ConfigManager configManager;

    @Autowired
    private CryptoManager cryptoManager;

    @Autowired
    private UserManagerDAO dao;

    @Override
    public void createUser(User user, String password) {
        if(user.getFirstName()==null || user.getFirstName().trim().equals("")){
            throw new BloggerException(ErrorCodes.FIRST_NAME_EMPTY);
        }
        if(user.getLastName()==null || user.getLastName().trim().equals("")){
            throw new BloggerException(ErrorCodes.LAST_NAME_EMPTY);
        }
        if(user.getLoginUid()==null || user.getLoginUid().trim().equals("")){
            throw new BloggerException(ErrorCodes.LOGINUID_EMPTY);
        }
        if(user.getEmail()==null || user.getEmail().trim().equals("")){
            throw new BloggerException(ErrorCodes.EMAIL_EMPTY);
        }
        if(password==null || password.trim().equals("")){
            throw new BloggerException(ErrorCodes.PASSWORD_EMPTY);
        }

        UserEntity ue = dao.getUserFromLoginUid(user.getLoginUid().toLowerCase().trim());

        if(ue!=null){
            throw new BloggerException(ErrorCodes.LOGINUID_EXISTS);
        }

        ue = EntityHelper.getUserEntity(null, user);


        String alg = configManager.getDigestAlgorithm();
        byte[] pwd = password.getBytes();
        byte[] salt = cryptoManager.getSalt(pwd.length);
        byte[] hash = cryptoManager.encodePassword(pwd, salt, alg);

        CredentialEntity ce = new CredentialEntity();
        ce.setHash(cryptoManager.base64Encode(hash));
        ce.setSalt(cryptoManager.base64Encode(salt));
        ce.setUser(ue);

        dao.persistEntity(ue);
        dao.persistEntity(ce);

    }

    @Override
    public void updateUser(User user) {
        if(user.getId()==null || user.getId().trim().equals("")){
            if(user.getLoginUid()==null || user.getLoginUid().trim().equals("")){
                throw new BloggerException(ErrorCodes.USER_ID_LOGINUID_MISSING);
            }
        }

        UserEntity ue = dao.getEntity(UserEntity.class, user.getId());
        if(ue==null){
            ue = dao.getUserFromLoginUid(user.getLoginUid());
        }

        if(ue==null){
            throw new BloggerException(ErrorCodes.USER_NOT_FOUND);
        }
        if(user.getFirstName()==null || user.getFirstName().trim().equals("")){
            throw new BloggerException(ErrorCodes.FIRST_NAME_EMPTY);
        }
        if(user.getLastName()==null || user.getLastName().trim().equals("")){
            throw new BloggerException(ErrorCodes.LAST_NAME_EMPTY);
        }
        if(user.getEmail()==null || user.getEmail().trim().equals("")){
            throw new BloggerException(ErrorCodes.EMAIL_EMPTY);
        }

        ue.setFirstName(user.getFirstName());
        ue.setLastName(user.getLastName());
        ue.setEmail(user.getEmail());

        dao.persistEntity(ue);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, String userId) {
        if(newPassword==null || newPassword.trim().equals("")){
            throw new BloggerException(ErrorCodes.NEW_PASSWORD_EMPTY);
        }

        boolean verify = verifyCredential(userId, oldPassword);
        if(!verify){
            throw new BloggerException(ErrorCodes.OLD_PASSWORD_MISMATCH);
        }

        if(newPassword==null || newPassword.trim().equals("")){
            throw new BloggerException(ErrorCodes.PASSWORD_EMPTY);
        }

        String alg = configManager.getDigestAlgorithm();

        byte[] pwd = newPassword.getBytes();
        byte[] salt = cryptoManager.getSalt(pwd.length);
        byte[] hash = cryptoManager.encodePassword(pwd, salt, alg);

        CredentialEntity ce = dao.getCredential(userId);
        ce.setSalt(cryptoManager.base64Encode(salt));
        ce.setHash(cryptoManager.base64Encode(hash));
        dao.persistEntity(ce);

    }

    @Override
    public void updateUserLoginTime(String userId){
        UserEntity ue = dao.getEntity(UserEntity.class, userId);
        ue.setLastLoginTime(System.currentTimeMillis());
        dao.persistEntity(ue);
    }

    @Override
    public boolean verifyCredential(String userId, String password){
        CredentialEntity ce = dao.getCredential(userId);
        if(ce==null){
            return false;
        }

        String alg = configManager.getDigestAlgorithm();
        String hash = cryptoManager.base64Encode(cryptoManager.encodePassword(password.getBytes(), cryptoManager.base64Decode(ce.getSalt()), alg));
        if(!hash.equals(ce.getHash())){
            return false;
        }

        return true;

    }

    @Override
    public User getUserFromLoginUid(String loginUid){
        UserEntity ue = dao.getUserFromLoginUid(loginUid.trim().toLowerCase());
        if(ue==null){
            return null;

        }

        return DataHelper.getUser(ue);
    }

    @Override
    public User getUser(String userId){
        UserEntity ue = dao.getEntity(UserEntity.class, userId);
        if(ue==null){
            return null;

        }

        return DataHelper.getUser(ue);
    }


}
