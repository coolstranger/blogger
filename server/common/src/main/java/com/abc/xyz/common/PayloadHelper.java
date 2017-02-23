package com.abc.xyz.common;

import com.abc.xyz.common.data.Blog;
import com.abc.xyz.common.data.User;

import java.util.HashMap;

public class PayloadHelper {

    public static User getUser(HashMap<String,String> payload){
        User u = new User();
        u.setId(payload.get(PayloadConstants.U_ID));
        u.setFirstName(payload.get(PayloadConstants.U_FIRST_NAME));
        u.setLastName(payload.get(PayloadConstants.U_LAST_NAME));
        u.setEmail(payload.get(PayloadConstants.U_EMAIL));
        u.setLoginUid(payload.get(PayloadConstants.U_LOGIN));
        return u;
    }

    public static Blog getBlog(HashMap<String,String> payload){
        Blog b = new Blog();
        b.setId(payload.get(PayloadConstants.B_ID));
        b.setName(payload.get(PayloadConstants.B_NAME));
        b.setDesc(payload.get(PayloadConstants.B_DESC));
        b.setBody(payload.get(PayloadConstants.B_BODY));
        return b;
    }
}
