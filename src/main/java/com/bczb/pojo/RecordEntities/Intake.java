package com.bczb.pojo.RecordEntities;

import com.bczb.pojo.RecordEntities.RecType.RatElement;
import com.bczb.pojo.RecordEntities.RecType.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Intake {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IntakeElement extends RatElement {
        private Double initialWeight;
        private Double nextdayWeight;
        private Double intake;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IntakeRecord extends Record {
        private Double initialWeight;
        private Double nextdayWeight;
        private Double intake;
    }
}
