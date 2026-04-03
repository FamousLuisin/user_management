package com.sea.api.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CpfSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String cpf, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String cpfWithMask = String.format("%s.%s.%s-%s", 
            cpf.substring(0, 3),
            cpf.substring(3, 6),
            cpf.substring(6, 9),
            cpf.substring(9, 11)
        );
        
        gen.writeString(cpfWithMask);
    }
    
}
