package com.epam.esm.response.entities;

public class BaseResponseBody {

	private String message;
	private int code;

	public BaseResponseBody(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public BaseResponseBody() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
