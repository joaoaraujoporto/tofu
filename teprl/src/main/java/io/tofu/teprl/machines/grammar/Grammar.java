package io.tofu.teprl.machines.grammar;

import io.tofu.teprl.machines.Machine;

public class Grammar extends Machine {
	public static final Terminal EPSILON = new Terminal("tofuEpsilon");
	
	public Grammar(String name) {
		super(name);
	}
}
