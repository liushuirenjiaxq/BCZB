package com.bczb.pojo.RecordEntities;

import com.bczb.pojo.RecordEntities.RecType.RatElement;
import com.bczb.pojo.RecordEntities.RecType.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Feces {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FecesElement extends RatElement {
        private String photo;
        private String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FecesRecord extends Record {
        private String photo;
        private String description;
    }
}
