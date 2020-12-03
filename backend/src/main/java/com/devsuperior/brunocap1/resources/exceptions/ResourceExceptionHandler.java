package com.devsuperior.brunocap1.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.brunocap1.services.exceptions.DatabaseException;
import com.devsuperior.brunocap1.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status=HttpStatus.NOT_FOUND;//404
		
		StandardError err=new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
		HttpStatus status=HttpStatus.BAD_REQUEST;//400
		
		StandardError err=new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Database esception");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}

}
