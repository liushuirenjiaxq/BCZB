package com.bczb.pojo.RecordEntities;

import com.bczb.pojo.RecordEntities.RecType.Record;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecRecord<Rec extends Record> {
    private Integer id;
    private String gid;
    private String recType;
    private Integer recer;
    private String date;
    ArrayList<Rec> data;
}
