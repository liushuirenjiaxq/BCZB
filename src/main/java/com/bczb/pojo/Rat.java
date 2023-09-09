package com.bczb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rat {
    private Integer rId;

    private String gId;
    private String gender;
    private Integer cage;
    private String rIndex;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupRat {
        private String gender;
        private Integer cage;
        private String index;

    }

}
