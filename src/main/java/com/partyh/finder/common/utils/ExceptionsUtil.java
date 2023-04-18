package com.partyh.finder.common.utils;


import com.partyh.finder.common.exception.impl.PFValidationException;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ExceptionsUtil {

    private ExceptionsUtil(){}
    public static void validationEx(Map<String, String> errors){
        if(!errors.isEmpty()){
            throw new PFValidationException("Validation error", errors);
        }
    }
    public static void logExceptions(Logger log, Exception e) {
        log.error("Exception: {}", e.getMessage());
        if(log.isDebugEnabled()) {
            e.printStackTrace();
        }
    }

}
