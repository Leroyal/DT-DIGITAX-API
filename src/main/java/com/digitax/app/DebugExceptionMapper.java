package com.digitax.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.digitax.constants.ResponseConstants;
import com.digitax.payload.ApiRes;

@ControllerAdvice
public class DebugExceptionMapper {

    @SuppressWarnings("unchecked")
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, 
                HttpServletRequest request, HttpServletResponse response) {
    	 JSONObject statusObj = new JSONObject();
    	 statusObj.put("status_code",ResponseConstants.INTERNAL_SERVER_ERROR);
         statusObj.put("message", "FAILURE");
         JSONObject responseObj = new JSONObject();
         statusObj.put("error_message", ex.getMessage());
         return new ResponseEntity<>(ApiRes.success(responseObj, statusObj), HttpStatus.OK);
    }
}