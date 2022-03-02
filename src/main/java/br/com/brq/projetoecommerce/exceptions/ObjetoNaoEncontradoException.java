package br.com.brq.projetoecommerce.exceptions;

@SuppressWarnings("serial")
public class ObjetoNaoEncontradoException extends RuntimeException {

	public ObjetoNaoEncontradoException(String msg) {
		super(msg);
	}
}
