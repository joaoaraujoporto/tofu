package io.tofu.teprl.machines.exceptions;

public class OperarMecanismoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OperarMecanismoException(String mensagemErro) {
		super(mensagemErro);
	}
}
