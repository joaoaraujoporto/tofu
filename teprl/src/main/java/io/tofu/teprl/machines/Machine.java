package io.tofu.teprl.machines;

public abstract class Machine {
	protected String name;
	
	public Machine(String nome) {
		this.name = nome;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	public String getName() {
		return name;
	}
}

