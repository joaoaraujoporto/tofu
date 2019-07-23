package io.tofu.tepas;

public abstract class Function<T> {
	Function<?> args[];
	
	public Function() {}
	
	public Function(Function<?> ... args) {
		this.args = args;
	}
	
	public abstract T apply();
}
