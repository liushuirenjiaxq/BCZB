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
    private String name;
    private int power;
    private String password;
    private String tele;
    private int status;

    public User( Integer id, String password, String name, int power, String tele, int status) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.power = power;
        this.tele = tele;
        this.status = status;
    }
    public String toString(){
        return this.name;
    }
}
