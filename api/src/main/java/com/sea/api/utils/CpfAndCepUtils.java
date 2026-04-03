package com.sea.api.utils;

public class CpfAndCepUtils {
    
    public static String normalize(String value){
        if (value == null) return null;
        
        return value.replaceAll("\\D", "");
    }
}
