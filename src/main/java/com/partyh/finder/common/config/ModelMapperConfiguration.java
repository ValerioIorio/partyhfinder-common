package com.partyh.finder.common.config;

import com.partyh.finder.common.config.mapper.CustomMapper;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Log4j2
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper(List<CustomMapper<?, ?>> customMappers) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        for(CustomMapper<?, ?> customMapper : customMappers) {
            customMapper.map(modelMapper);
            log.info("{} mapping added to model mapper", customMapper.getClass().getSimpleName());
        }
        return modelMapper;
    }
}
