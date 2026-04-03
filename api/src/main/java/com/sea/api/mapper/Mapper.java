package com.sea.api.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    
    private final ModelMapper mapper = new ModelMapper();

    public <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public <O, D> List<D> parseListObject(Collection<O> origin, Class<D> destination){
        return origin.stream().map(obj -> mapper.map(obj, destination)).collect(Collectors.toList());
    }
}
