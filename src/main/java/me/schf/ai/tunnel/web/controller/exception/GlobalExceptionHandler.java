package me.schf.ai.tunnel.web.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong :(");
	}

}
