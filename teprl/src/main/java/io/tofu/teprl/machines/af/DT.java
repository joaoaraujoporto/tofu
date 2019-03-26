package io.tofu.teprl.machines.af;

public class DT extends AF {
	private DTState currState;
	
	public DT(String nome) {
		super(nome);
	}
	
	public void init() {
		currState = getInitialState();
	}
	
	/*
	 * Verify if input is accepted or rejected
	 */
	public DTOutput a(String input) {
		boolean accepted;
		boolean back;
		
		for (char c : input.toCharArray()) {
			
		}
			
		
		
		return new DTOutput(,);
	}
	
	/*
	 * Do a transition as the currState and symbol c
	 */
	public void b(char c) {
		Transicao t = getTransicao(currState, String.valueOf(c));
		
		if (t == null)
			currState = null;
		
		currState = getEstado(t.getIndicesEstadosEntrada().get(0));
	}
}
