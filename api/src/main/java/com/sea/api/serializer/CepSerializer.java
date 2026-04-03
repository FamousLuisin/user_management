package com.sea.api.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CepSerializer extends JsonSerializer<String>{

    @Override
    public void serialize(String cep, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String cepWithMask = "";
        
        for (int i = 0; i < cep.length(); i++) {
            if (i == 5) {
                cepWithMask = cepWithMask.concat("-");
            }

            cepWithMask = cepWithMask.concat(String.valueOf(cep.charAt(i)));
        }

        gen.writeString(cepWithMask);
    }
    
}
