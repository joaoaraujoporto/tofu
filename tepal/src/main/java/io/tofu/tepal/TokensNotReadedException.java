package io.tofu.tepal;

public class TokensNotReadedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TokensNotReadedException() {
		super("There is no token readed by AL");
	}
}
