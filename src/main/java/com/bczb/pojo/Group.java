package com.bczb.pojo;

import com.bczb.pojo.Rat.GroupRat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    private String gId;
    private Integer exId;
    private String name;
    private int status;

    public static enum Status{
        DELETED,NORMAL, 
    }

    public static boolean judgeStatus(int status) {
        if(status == Status.NORMAL.ordinal() || status == Status.DELETED.ordinal()) {
            return true;
        }
        return false;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RatGroup {
        private Integer exId;
        private String name;
        private ArrayList<GroupRat> rats;
    }


}
