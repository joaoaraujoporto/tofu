package io.tofu.tepas.functions;

public abstract class Function<T> {
	Object args[];
	
	public Function() {}
	
	public Function(Object ... args) {
		this.args = args;
	}
	
	public abstract T apply();
}
