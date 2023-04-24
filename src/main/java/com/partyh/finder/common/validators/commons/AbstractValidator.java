package com.partyh.finder.common.validators.commons;


import com.partyh.finder.common.exception.impl.PFValidationException;
import org.springframework.http.HttpMethod;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class for validation of DTOs
 * @param <DTO> the DTO to validate
 * */
public abstract class AbstractValidator<DTO> {

    /**
     * Abstract method for validation of DTOs, it must be implemented in the concrete class, and called to validate the DTO using custom or inherited methods.
     * I wisely suggest create a LinkedHashMap<String, String> to collect all errors and throw the exception at the end of the method with the throwExceptionIfNecessary method.
     *
     * @param input the DTO to validate
     * @param method the HTTP method used to call the endpoint
     * @param object_presence a hashmap to specify if the object in the dto is present or not after fetching it from the database
     *
     * @throws PFValidationException if the DTO is not valid
     * */
    public abstract void validate(DTO input, HttpMethod method, LinkedHashMap<String, Boolean> object_presence);


    /**
     * Abstract method for validation of DTOs, it must be implemented in the concrete class, and called to validate the DTO using custom or inherited methods.
     * I wisely suggest create a LinkedHashMap<String, String> to collect all errors and throw the exception at the end of the method with the throwExceptionIfNecessary method.
     *
     * @param input the DTO to validate
     * @param method the HTTP method used to call the endpoint
     *
     * @throws PFValidationException if the DTO is not valid
     * */
    public abstract void validate(DTO input, HttpMethod method);

    /**
     * Method to validate the id only, of a DTO
     * @param id the id to validate
     * @param method the HTTP method used to call the endpoint, it has to be null if the method is Post, otherwise it has to be not null
     * @throws PFValidationException if the id is not valid for the method used
     * */
    public void validateId(Long id, HttpMethod method){
        LinkedHashMap<String, String> errors = new LinkedHashMap<>();
        errors.putAll(validateId(id, method, errors));
        throwExceptionIfNecessary(errors);
    }

    /**
     *  Method to throw a ValidationException if the LinkedHashMap<String, String> is not empty
     *  @param errors the LinkedHashMap<String, String> of errors
     * @throws PFValidationException if the LinkedHashMap<String, String> is not empty
     * */
    protected void throwExceptionIfNecessary(LinkedHashMap <String, String> errors){
        if (!errors.isEmpty()){
            throw new PFValidationException("Validation Error", errors);
        }
    }

    /**
     *Method to validate the id of a DTO
     * @param id the id to validate
     * @param method the HTTP method used to call the endpoint
     * @param errors the LinkedHashMap<String, String> to collect errors
     *
     * @return a LinkedHashMap<String, String> with the errors found
     * */
    protected Map<String, String> validateId(Long id, HttpMethod method, LinkedHashMap<String, String> errors) {
        if (method == HttpMethod.PUT || method == HttpMethod.DELETE || method == HttpMethod.GET) {
            if (id == null) {
                errors.put("id", "Id is mandatory");
            }
        }else if (method == HttpMethod.POST) {
            if (id != null) {
                errors.put("id", "Id must be null");
            }
        }

        return errors;
    }

    /**
     * Method to validate emails
     * @param email the email to validate
     * @param errors the LinkedHashMap<String, String> to collect errors
     *
     *@return a LinkedHashMap<String, String> with the errors found
     * */
    protected LinkedHashMap<String, String> validateEmail(String email, LinkedHashMap<String, String> errors) { //TODO: implementa un api per questo controllo
        // Check format of email
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            errors.put("email", "Invalid email format");
        }
        // Check domain of email
        else {
            String[] emailParts = email.split("@");
            String domain = emailParts[1];
            if (!domain.contains(".")) {
                errors.put("email", "Invalid email domain");
            }
        }

        return errors;
    }

    /**
     * Method to validate LocalDates
     * @param date the LocalDate to validate
     * @param beforeOrAfterNow the LocalDate must be before or after today
     *@return a LinkedHashMap<String, String> with the errors found
     * */
    protected LinkedHashMap<String, String> validateLocalDate(LocalDate date, String beforeOrAfterNow, LinkedHashMap <String, String> errors){
        if (date != null){
            if (beforeOrAfterNow.equals("before")){
                if (date.isAfter(LocalDate.now())){
                    errors.put("dateOfBirth", "Date of Birth must be before today");
                }
            }else if (beforeOrAfterNow.equals("after")){
                if (date.isBefore(LocalDate.now())){
                    errors.put("dateOfBirth", "Date of Birth must be after today");
                }
            }
        }else {
            errors.put("dateOfBirth", "Date of Birth is mandatory");
        }
        return errors;
    }

    /**
     * Method to validate mandatory fields
     * @param fields the fields to validate
     * @param errors the LinkedHashMap<String, String> to collect errors
     *
     *@return a LinkedHashMap<String, String> with the errors found
     * */
    protected LinkedHashMap<String, String> validateMandatoryFields(LinkedHashMap <String, String> errors, List<String> mandatoryFieldsName , String ... fields){
        for (int i = 0; i < fields.length; i++){
            if (fields[i] == null || fields[i].isEmpty()){
                errors.put(mandatoryFieldsName.get(i), mandatoryFieldsName.get(i) + " is mandatory");
            }
        }
        return errors;
    }


    /**
     * Method to validate the presence of optional objects
     * @param objects_presence the LinkedHashMap<String, Boolean> representing a key-value pair of the name of the object, and a boolean that indicates if the object is present
     * @param errors the LinkedHashMap<String, String> to collect errors
     * */
    protected LinkedHashMap<String, String> validatePresenceOfOptionalObject(LinkedHashMap<String, Boolean> objects_presence, LinkedHashMap<String, String> errors){
        objects_presence.forEach((key, value) -> {
            if (!value){
                errors.put(key, key + " was not found");
            }
        });
        return errors;
    }


    protected LinkedHashMap<String, String> validateFieldEqualsToSpecificValues(LinkedHashMap<String, String> errors, String fieldName, Object fieldValue, Object ... values){
        boolean found = false;
        for (Object value : values){
            if (value.equals(fieldValue)){
                found = true;
                break;
            }
        }
        if (!found){
            errors.put(fieldName, fieldName + " is not valid, allowed values are: " + Arrays.toString(values));
        }
        return errors;
    }

}
