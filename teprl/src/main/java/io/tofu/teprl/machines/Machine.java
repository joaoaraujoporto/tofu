package io.tofu.teprl.machines;

public abstract class Machine {
	protected String name;
	
	public Machine(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	public String getName() {
		return name;
	}
}

