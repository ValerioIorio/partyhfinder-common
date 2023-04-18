package com.partyh.finder.common.utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import static com.partyh.finder.common.validators.commons.ValidationUtils.validationFormatLocalDateTime;


@Log4j2
@Getter
public class FilterUtil {

    private static final Map<String,String> sortMappings;

    static
    {
        sortMappings = new HashMap<>();
        sortMappings.put("idFamily","family.id");
    }
    private FilterUtil(){
    }
    public static int getNumberOfPage(Integer numberOfPage) {
        if(numberOfPage != null && numberOfPage>0) {
            return numberOfPage-1;
        }
        return 0;
    }
    public static int getPageSize(Integer pageSize) {
        if(pageSize != null) {
            return pageSize;
        }
        return 10;
    }
    public static Sort.Direction getDirection(Boolean descending) {
        if(descending!= null && !descending) {
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

    public static String getSortBy(String sortBy) {
        if(sortBy == null) {
            return  "id";
        }else if(sortMappings.containsKey(sortBy)){
            return sortMappings.get(sortBy);
        }
        return sortBy;
    }
    public static LocalDateTime getDate(String field, String date) {
        if(date != null) {
            date = validationFormatLocalDateTime(date, field);
            if(date.length() > 19) {
                throw new IllegalArgumentException(
                        "You write the ".concat(field).concat(" field correctly"));
            }
            return LocalDateTime.parse(date);
        }
        return null;
    }
}
