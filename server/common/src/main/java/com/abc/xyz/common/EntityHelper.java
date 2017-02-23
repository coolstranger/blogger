package com.abc.xyz.common;

import com.abc.xyz.common.data.Blog;
import com.abc.xyz.common.data.User;
import com.abc.xyz.schema.BlogEntity;
import com.abc.xyz.schema.UserEntity;


public class EntityHelper {

    public static UserEntity getUserEntity(UserEntity ue, User u){
        if(ue==null){
            ue = new UserEntity();
        }

        ue.setFirstName(u.getFirstName());
        ue.setLastName(u.getLastName());
        ue.setLoginUid(u.getLoginUid().toLowerCase().trim());
        ue.setEmail(u.getEmail());

        return ue;
    }

    public static BlogEntity getBlogEntity(BlogEntity be, Blog b){
        if(be==null)
            be = new BlogEntity();

        be.setName(b.getName());
        be.setDesc(b.getDesc());
        be.setBody(b.getBody());
        return be;
    }
}
