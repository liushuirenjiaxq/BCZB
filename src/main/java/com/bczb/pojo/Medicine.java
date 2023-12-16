package com.bczb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {
  private int id;
  private String name;
  private String latinName;
  private String origin;
  private String medicinalParts;
  private String proArea;
  private String harvestTime;
  private String flavor;
  private String channelTropism;
  private String effect;
  private String usageDosage;
  private String identifyXz;
  private String identifyXw;
  private String identifyLh;
  private String identifyHl;
  private String primaryApplication;
}
