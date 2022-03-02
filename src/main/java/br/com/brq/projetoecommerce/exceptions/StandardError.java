package br.com.brq.projetoecommerce.exceptions;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StandardError {

	protected Date timestamp;
	protected Integer status;
	protected String error;
	protected String message;
	protected String path;
}