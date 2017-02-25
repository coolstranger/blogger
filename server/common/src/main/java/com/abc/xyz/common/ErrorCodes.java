package com.abc.xyz.common;

public class ErrorCodes {

    //UserManagerError Codes
    public static final int FIRST_NAME_EMPTY = 0x1001;
    public static final int LAST_NAME_EMPTY = 0x1002;
    public static final int EMAIL_EMPTY = 0x1003;
    public static final int LOGINUID_EMPTY = 0x1004;
    public static final int PASSWORD_EMPTY = 0x1005;
    public static final int LOGINUID_EXISTS = 0x1006;
    public static final int PASSWORD_MISMATCH = 0x1007;
    public static final int OLD_PASSWORD_MISMATCH = 0x1008;
    public static final int UID_PASSWORD_MISMATCH = 0x1008;
    public static final int USER_ID_LOGINUID_MISSING = 0x1009;
    public static final int USER_NOT_FOUND = 0x100A;
    public static final int NEW_PASSWORD_EMPTY = 0x100B;
    public static final int FIELD_LENGTH_EXCEEDED = 0x100C;


    //BlogManagerErrorCodes
    public static final int BLOG_NAME_EMPTY = 0x2001;
    public static final int BLOG_DESC_EMPTY = 0x2002;
    public static final int BLOG_BODY_EMPTY = 0x2003;
    public static final int BLOG_USER_NOT_FOUND = 0x2004;
    public static final int BLOG_NOT_FOUND = 0x2005;
    public static final int BLOG_ID_EMPTY = 0x2006;
    public static final int BLOG_ANOTHER_USER = 0x2007;


    //AuthenticationManager Error Codes
    public static final int NO_WEB_SESSION_PRESENT = 0x3001;
    public static final int AUTHENTICATION_FAILED = 0x3002;


    public static void init() {

        ErrorMessage.addMessage(FIRST_NAME_EMPTY, "First Name is empty");
        ErrorMessage.addMessage(LAST_NAME_EMPTY, "Last Name is empty");
        ErrorMessage.addMessage(EMAIL_EMPTY, "Email is empty");
        ErrorMessage.addMessage(LOGINUID_EMPTY, "Login Id is Empty");
        ErrorMessage.addMessage(PASSWORD_EMPTY, "Password is Empty");
        ErrorMessage.addMessage(LOGINUID_EXISTS, "Login Id already exists");
        ErrorMessage.addMessage(PASSWORD_MISMATCH, "Password and Confirm password do not match");
        ErrorMessage.addMessage(OLD_PASSWORD_MISMATCH, "Old password did not matched");
        ErrorMessage.addMessage(UID_PASSWORD_MISMATCH, "User ID and Password did not matched");
        ErrorMessage.addMessage(USER_ID_LOGINUID_MISSING, "User Id or User LoginId must be present");
        ErrorMessage.addMessage(USER_NOT_FOUND, "User not found with given id or loginuid");
        ErrorMessage.addMessage(NEW_PASSWORD_EMPTY, "New Password Cannot be empty");
        ErrorMessage.addMessage(FIELD_LENGTH_EXCEEDED, "Field Length Cannot be more than 255");


        ErrorMessage.addMessage(BLOG_NAME_EMPTY, "Blog Name is Empty");
        ErrorMessage.addMessage(BLOG_DESC_EMPTY, "Blog Description is Empty");
        ErrorMessage.addMessage(BLOG_BODY_EMPTY, "Blog Body is Empty");
        ErrorMessage.addMessage(BLOG_USER_NOT_FOUND, "Blog User not Found");
        ErrorMessage.addMessage(BLOG_NOT_FOUND, "Blog not Found");
        ErrorMessage.addMessage(BLOG_ID_EMPTY, "Blog Id is Empty");
        ErrorMessage.addMessage(BLOG_ANOTHER_USER , "Operation Not permitted, Not Blog Owner");

        ErrorMessage.addMessage(NO_WEB_SESSION_PRESENT , "No User Session Present");
        ErrorMessage.addMessage(AUTHENTICATION_FAILED , "User Authentication Failed");



    }

}
