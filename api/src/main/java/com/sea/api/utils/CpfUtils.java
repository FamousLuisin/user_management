package com.sea.api.utils;

public class CpfUtils {
    
    public static String normalize(String cpf){
        if (cpf == null) return null;
        
        return cpf.replaceAll("\\D", "");
    }
}
