package br.com.brq.projetoecommerce.exceptions;

public class EnderecoNaoEncontradoException extends RuntimeException {
	
	public EnderecoNaoEncontradoException(String messageError) {
		super(messageError);
		}
	}