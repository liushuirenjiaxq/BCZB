package com.bczb.pojo;

import lombok.Data;

@Data
public class User {
    public enum Power {
        Admin,
        User,
        Visitor,
        Test;
    }
    public enum Status {
        Deleted,
        Normal,
        Test;
    }
    private Integer id;
    private String password;
    private String name;
    private int power;
    private String tele;
    private int status;
}
