package io.tofu.teprl.machines.af;

public class DTState extends State {
	private boolean backable;
	private Action action;
	
	public DTState(Integer numero, String nome, Boolean inicial,
			Boolean aceitacao, boolean backable, Action action) {
		super(numero, nome, inicial, aceitacao);
		this.backable = backable;
		this.action = action;
	}
	
	public boolean isBackable() {
		return backable;
	}
	
	public Action getAction() {
		return action;
	}
}
