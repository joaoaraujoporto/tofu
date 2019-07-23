package io.tofu.tepas;

public abstract class Procedure {
	Function<?>[] args;
	
	public Procedure() {}
	
	public Procedure(Function<?> ... args) {
		this.args = args;
	}
	
	public abstract void apply();
}
