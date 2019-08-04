package io.tofu.tepas;

public abstract class Procedure {
	Function<?>[] args;
	String name;
	
	public Procedure() {name = getClass().getName();}
	
	public Procedure(Function<?> ... args) {
		this();
		this.args = args;
	}
	
	public abstract void apply();
	
	public String getName() {
		return name;
	}
}
