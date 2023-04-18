package com.partyh.finder.common.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class StringDateConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(MappingContext<String, LocalDateTime> context) {
        String source = context.getSource();

        if(StringUtils.hasText(source)){
            try{
                return LocalDateTime.parse(source);
            }catch(Exception e){
                //mettere log in debug
            }
        }
        return null;
    }
}
