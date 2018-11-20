package com.example.demo.controllers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler {

	@ExceptionHandler(BindException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(BindException ex, WebRequest request) {
		Map<String, Object> response = new HashMap<>();
		response.put("status", "error");
		response.put("message", ex.getBindingResult().getFieldErrors().stream().map(a -> a.getField().toLowerCase() +" "+ a.getDefaultMessage()).collect(Collectors.joining(" and ")));
		return ResponseEntity.ok(response);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		Map<String, Object> response = new HashMap<>();
		response.put("status", "error");
		response.put("message", "error : " + ex.getMessage());
		return ResponseEntity.ok(response);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		binder.registerCustomEditor(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

//	@ExceptionHandler(RecordNotFoundException.class)
//	public final ResponseEntity<Object> handleNotFoundException(RecordNotFoundException ex, WebRequest request) {
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false), "error");
//		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//	}
//	
//	@ExceptionHandler(BadRequestException.class)
//	public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
//		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false), "error");
//		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
//	}
}
