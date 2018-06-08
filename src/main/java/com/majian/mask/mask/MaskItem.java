package com.majian.mask.mask;



public interface MaskItem {


    String getJsonPath();

    int getFrom();

    int getTo();

    boolean isAccountExcluded(String account);

}
