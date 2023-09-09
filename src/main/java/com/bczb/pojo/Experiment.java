package com.bczb.pojo;

public class Experiment {

    public static  enum Status {
        CREATED, RUNNING, FINISHED,DELETED
    }

    public static boolean judgeStatus(int status) {
        return status == Status.CREATED.ordinal() || status == Status.RUNNING.ordinal() || status == Status.FINISHED.ordinal() || status == Status.DELETED.ordinal();
    }

    private int exId;
    private String name;
    private String startDate;
    private String endDate;
    private String ratName;
    private int status;
    private int ownerId;

    public Experiment(int exId, String name, String startDate, String endDate, String ratName, int status, int ownerId) {
        this.exId = exId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ratName = ratName;
        this.status = status;
        this.ownerId = ownerId;
    }

    public Experiment() {
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getExId() {
        return exId;
    }

    public void setExId(int exId) {
        this.exId = exId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRatName() {
        return ratName;
    }

    public void setRatName(String ratName) {
        this.ratName = ratName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
