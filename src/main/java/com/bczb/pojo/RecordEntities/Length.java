package com.bczb.pojo.RecordEntities;

import com.bczb.pojo.RecordEntities.RecType.RatElement;
import com.bczb.pojo.RecordEntities.RecType.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Length {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LengthElement extends RatElement {
        private Double length;
    }
    
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LengthRecord extends Record {
        private Double length;
    }
}
