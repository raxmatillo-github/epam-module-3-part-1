package com.epam.esm.response.entities;

public class BaseResponse {

	private int httpStatus;
	private BaseResponseBody responseBody;

	public BaseResponse(int httpStatus, BaseResponseBody responseBody) {
		this.httpStatus = httpStatus;
		this.responseBody = responseBody;
	}

	public BaseResponse() {
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public BaseResponseBody getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(BaseResponseBody responseBody) {
		this.responseBody = responseBody;
	}

}
