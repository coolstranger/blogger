package com.abc.xyz.endpoint;


import com.abc.xyz.common.BloggerException;

public class PayloadClasses {

    public static class ExceptionPayload {

        private int errorCode;
        private String message;
        private String cause;

        public ExceptionPayload(Throwable t){
            if(t instanceof BloggerException){
                BloggerException e = (BloggerException) t;
                errorCode = e.getErrorCode();
                message = e.getMessage();
                if(e.getCause()!=null){
                    cause = e.getCause().getMessage();
                }
            }
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCause() {
            return cause;
        }

        public void setCause(String cause) {
            this.cause = cause;
        }
    }

}
