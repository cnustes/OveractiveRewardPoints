package com.overactive.milo.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ParameterMessages 
{
	public static final String ERROR_NO_DATA = "No information was found.";
	public static final String ERROR_SERVER = "Internal server error.";
	public static final String RESPONSE_OK = "No errors occurred.";
	public static final String RESPONSE_OK_CREATE = "No errors occurred, has been created.";
	
	public static String formatMessage( BindingResult result)
	{
        List<Map<String,String>> errors = result.getFieldErrors().stream()
	        .map(err ->{
	            Map<String,String>  error =  new HashMap<>();
	            error.put(err.getField(), err.getDefaultMessage());
	            return error;
	
	        }).collect(Collectors.toList());
        
        ErrorMessage errorMessage = ErrorMessage.builder()
	        .code("01")
	        .messages(errors).build();
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        
        try 
        {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) 
        {
            e.printStackTrace();
        }
        
        return jsonString;
    }

}
