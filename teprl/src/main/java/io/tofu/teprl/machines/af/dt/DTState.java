package io.tofu.teprl.machines.af.dt;

import java.lang.reflect.Method;

import io.tofu.teprl.machines.af.State;

public class DTState extends State {
	private boolean backable;
	private Method action;
	
	public DTState(String name, Boolean inicial,
			Boolean aceitacao, boolean backable, Method action) {
		super(name, inicial, aceitacao);
		this.backable = backable;
		this.action = action;
	}
	
	public boolean isBackable() {
		return backable;
	}
	
	public Method getAction() {
		return action;
	}
}
