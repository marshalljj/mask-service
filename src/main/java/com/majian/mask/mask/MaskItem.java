package com.majian.mask.mask;



public interface MaskItem {


    String getJsonPath();

    Integer getFrom();

    Integer getTo();

    boolean isAccountExcluded(String account);

}
