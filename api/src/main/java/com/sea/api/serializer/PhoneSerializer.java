package com.sea.api.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PhoneSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String phone, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (phone.length() == 11) gen.writeString(cellPhone(phone));

        else gen.writeString(CommercialAndResidentialPhone(phone));
    }

    private String cellPhone(String phone){
        return String.format("(%s) %s %s-%s",
            phone.substring(0, 2),
            phone.substring(2, 3),
            phone.substring(3, 7),
            phone.substring(7, 11)
        );
    }
    
    private String CommercialAndResidentialPhone(String phone){
        return String.format("(%s) %s-%s",
            phone.substring(0, 2),
            phone.substring(2, 6),
            phone.substring(6, 10)
        );
    }
}
