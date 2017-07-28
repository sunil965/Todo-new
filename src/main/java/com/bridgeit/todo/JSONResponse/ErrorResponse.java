package com.bridgeit.todo.JSONResponse;

import java.util.List;

import org.springframework.validation.FieldError;

public class ErrorResponse extends Response
{
	Exception e;
	List<FieldError> list;

	public List<FieldError> getList() {
		return list;
	}

	public void setList(List<FieldError> list) {
		this.list = list;
	}

	public Exception getE() {
		return e;
	}

	public void setE(Exception e) {
		this.e = e;
	}
}
