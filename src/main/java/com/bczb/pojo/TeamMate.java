package com.bczb.pojo;

public class TeamMate {
    private int id;
    private int exId;
    private Long uId;
    private String joinDate;

    public TeamMate(int id, int exId, Long uId, String joinDate) {
        this.id = id;
        this.exId = exId;
        this.uId = uId;
        this.joinDate = joinDate;
    }

    public TeamMate() {
    }


    @Override
    public String toString() {
        return "Team [id=" + id + ", exId=" + exId + ", uId=" + uId + ", joinDate=" + joinDate + "]";
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExId() {
        return exId;
    }

    public void setExId(int exId) {
        this.exId = exId;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }


    
}
