package com.sea.api.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CpfSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String cpf, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String cpfWithMask = "";
        
        for (int i = 0; i < cpf.length(); i++) {
            if (i == 3 || i == 6) cpfWithMask = cpfWithMask.concat(".");
            
            if (i == 9) cpfWithMask = cpfWithMask.concat("-") ;
            
            cpfWithMask = cpfWithMask.concat(String.valueOf(cpf.charAt(i)));
        }

        gen.writeString(cpfWithMask);
    }
    
}
