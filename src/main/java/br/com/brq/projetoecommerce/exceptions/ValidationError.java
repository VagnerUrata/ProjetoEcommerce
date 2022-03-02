package br.com.brq.projetoecommerce.exceptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValidationError extends StandardError {

	private List<FieldError> errors;

	public ValidationError(Date timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
		this.errors = new ArrayList<>();
	}

	public void addError(String fieldName, String message) {
		this.errors.add(FieldError.builder().fieldName(fieldName).message(message).build());
	}

}