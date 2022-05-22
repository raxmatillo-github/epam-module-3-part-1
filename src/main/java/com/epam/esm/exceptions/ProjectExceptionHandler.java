package com.epam.esm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.epam.esm.response.entities.BaseResponse;
import com.epam.esm.response.entities.BaseResponseBody;

@ControllerAdvice
public class ProjectExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<BaseResponse> handleException(NumberFormatException e) {
		BaseResponseBody responseBody = new BaseResponseBody("Path variable is not a valid number", 99999);
		BaseResponse responseEntity = new BaseResponse(HttpStatus.BAD_REQUEST.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<BaseResponse> handleException(NotFoundException e) {
		BaseResponseBody responseBody = new BaseResponseBody(e.getMessage(), 99999);
		BaseResponse responseEntity = new BaseResponse(HttpStatus.NOT_FOUND.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<BaseResponse> handleException(AlreadyExistException e) {
		BaseResponseBody responseBody = new BaseResponseBody(e.getMessage(), 99999);
		BaseResponse responseEntity = new BaseResponse(HttpStatus.BAD_REQUEST.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<BaseResponse> handleException(FieldRequiredException e) {
		BaseResponseBody responseBody = new BaseResponseBody(e.getMessage(), 99999);
		BaseResponse responseEntity = new BaseResponse(HttpStatus.BAD_REQUEST.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<BaseResponse> handleException(GenericException e) {
		BaseResponseBody responseBody = new BaseResponseBody(e.getMessage(), 99999);
		BaseResponse responseEntity = new BaseResponse(HttpStatus.BAD_REQUEST.value(), responseBody);
		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler
//	public ResponseEntity<BaseResponse> handleException(Exception e) {
//		BaseResponseBody responseBody = new BaseResponseBody(e.getMessage(), 99999);
//		BaseResponse responseEntity = new BaseResponse(HttpStatus.BAD_REQUEST.value(), responseBody);
//		return new ResponseEntity<>(responseEntity, HttpStatus.BAD_REQUEST);
//	}
}
