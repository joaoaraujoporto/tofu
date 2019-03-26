package io.tofu.teprl.machines.af.dt;

import java.lang.reflect.Method;

import io.tofu.teprl.machines.af.AF;
import io.tofu.teprl.machines.af.State;
import io.tofu.teprl.machines.af.Transicao;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;

public class DT extends AF {
	private DTState currState;
	
	public DT(String nome) {
		super(nome);
	}
	
	public void createState(String name, Boolean inicial,
			Boolean aceitacao, boolean backable, Method action) throws EditarMecanismoException {
		DTState s = new DTState(name, inicial, aceitacao, backable, action);
		addState(s);
	}
	
	@Override
	public void addState(State s) throws EditarMecanismoException {
		if (!(s instanceof DTState))
			throw new EditarMecanismoException("A state to DT must be a DTState");
		
		super.addState(s);
	}
	
	public void init() {
		currState = (DTState) getInitialState();
	}
	
	/*
	 * Verify if input is accepted or rejected
	 */
	public void a(String input) {
		for (char c : input.toCharArray())
			b(c);
	}
	
	/*
	 * Do a transition according to currState and symbol c
	 */
	public Method b(char c) {
		if (currState == null)
			throw new EditarMecanismoException("A state to DT must be a DTState"); // TODO - operateMachineException
		
		Transicao t = getTransicao(currState, String.valueOf(c));
		
		if (t == null)
			currState = null;
		
		currState = (DTState) getEstado(t.getIndicesEstadosEntrada().get(0));
		
		return currState.getAction();
	}
	
	public DTState getCurrState() {
		return currState;
	}
}
