package com.abc.xyz.common;


import com.abc.xyz.common.data.Blog;
import com.abc.xyz.common.data.User;
import com.abc.xyz.schema.BlogEntity;
import com.abc.xyz.schema.UserEntity;

public class DataHelper {

    public static Blog getBlog(BlogEntity be){
        Blog b = new Blog();
        b.setId(be.getId());
        b.setName(be.getName());
        b.setDesc(be.getDesc());
        b.setBody(be.getBody());
        b.setCreationDate(be.getCreationDate());
        b.setLastUpdate(be.getLastUpdate());
        b.setRevision(be.getRevision());
        return b;
    }

    public static User getUser(UserEntity ue){
        User u = new User();
        u.setId(ue.getId());
        u.setFirstName(ue.getFirstName());
        u.setLastName(ue.getLastName());
        u.setEmail(ue.getEmail());
        u.setLoginUid(ue.getLoginUid());
        u.setLastLoginTime(ue.getLastLoginTime());
        return u;
    }
}
