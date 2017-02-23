package com.abc.xyz.common;


public class BloggerException extends RuntimeException{

    public BloggerException(){}

    public BloggerException(int errorCode){
        super(ErrorMessage.getMessage(errorCode));
        this.errorCode = errorCode;
    }

    public BloggerException(Throwable t){
        super(t);
    }

    public BloggerException(int errorCode, Throwable t){
        super(ErrorMessage.getMessage(errorCode), t);
        this.errorCode = errorCode;
    }

    public BloggerException(int errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    protected int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }




}
