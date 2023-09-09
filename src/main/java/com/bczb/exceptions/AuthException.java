package com.bczb.exceptions;

public class AuthException extends Exception {
    private static final long serialVersionUID = 1L;
    private String msg;

    public String getMsg() {
        return msg;
    }



    public AuthException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
