package io.tofu.tepas.procedures;

public abstract class Procedure {
	protected Object[] args;
	protected String name;
	
	public Procedure() {name = getClass().getSimpleName();}
	
	public Procedure(Object ... args) {
		this();
		this.args = args;
	}
	
	public abstract void apply();
	
	public String getName() {
		return name;
	}
}
