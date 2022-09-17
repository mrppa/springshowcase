package com.mrppa.springrestshowcase.base;

import lombok.Getter;

@Getter
public class CustomServiceException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ErrorCodes errorCode;

	public CustomServiceException(ErrorCodes errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public CustomServiceException() {
		this(null);
	}
}
