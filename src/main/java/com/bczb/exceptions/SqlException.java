package com.bczb.exceptions;

public class SqlException extends Exception{
    private static final long serialVersionUID = 1L;
    private String msg;

    public SqlException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
