package io.tofu.teprl.machines.af.dt;

import java.lang.reflect.Method;

import io.tofu.teprl.machines.af.State;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;

public class DTState extends State {
	private boolean backable;
	
	public DTState(String name, Boolean start,
			Boolean accept, Boolean dead,
			Boolean backable) throws EditarMecanismoException {
		super(name, start, accept, dead);
		
		this.backable = backable;
	}
	
	public boolean isBackable() {
		return backable;
	}
}
