package com.overactive.milo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class ParameterMessages 
{
	public static final String ERROR_NO_DATA = "No information was found.";
	public static final String ERROR_SERVER = "Internal server error.";
	public static final String RESPONSE_OK = "No errors occurred.";
	public static final String RESPONSE_OK_CREATE = "No errors occurred, has been created.";
	public static final String BAD_REQUEST = "Invalid syntax.";
	
	public static ErrorMessage formatMessage(BindingResult result)
	{
        List<Map<String,String>> errors = result.getFieldErrors().stream()
	        .map(err ->{
	            Map<String,String>  error =  new HashMap<>();
	            error.put(err.getField(), err.getDefaultMessage());
	            return error;
	
	        }).collect(Collectors.toList());
        
        ErrorMessage errorMessage = ErrorMessage.builder()
	        .messages(errors).build();
        
        return errorMessage;
    }
	
	public static ErrorMessage especificError(String key, String value)
	{
		Map<String,String> myMap = new HashMap<>();
		myMap.put(key, value);
	    
	    List<Map<String,String>> list = new ArrayList<>();
	    list.add(myMap);
	    
	    ErrorMessage errorMessage = ErrorMessage.builder()
		        .messages(list).build();
	    
	    return errorMessage;
	}

}
