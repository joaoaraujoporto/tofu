package io.tofu.teprl.machines.af.dt;

import io.tofu.teprl.machines.af.AF;
import io.tofu.teprl.machines.af.State;
import io.tofu.teprl.machines.af.Transition;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;
import io.tofu.teprl.machines.exceptions.OperarMecanismoException;

public class DT extends AF {
	private DTState currState;
	
	public DT(String nome) {
		super(nome);
	}
	
	public void createState(String name, Boolean start,
			Boolean accept, Boolean dead, Boolean backable) throws EditarMecanismoException {
		DTState s = new DTState(name, start, accept, dead, backable);
		addState(s);
	}
	
	@Override
	public void addState(State s) throws EditarMecanismoException {
		if (!(s instanceof DTState))
			throw new EditarMecanismoException("A state to DT must be a DTState");
		
		super.addState(s);
	}
	
	public void init() throws OperarMecanismoException {
		State startState = getInitialState();
		
		if (startState == null)
			throw new OperarMecanismoException("There is not a start state");
		
		currState = (DTState) startState;
	}
	
	/*
	 * Verify if input is accepted or rejected
	 */
	public void a(String input) throws OperarMecanismoException {
		for (char c : input.toCharArray())
			read(c);
	}
	
	/*
	 * Do a transition according to currState and symbol c
	 */
	public DTState read(char c) throws OperarMecanismoException {
		if (currState == null)
			throw new OperarMecanismoException("There is not a start state");
		
		Transition t = getTransicao(currState, String.valueOf(c));
		
		
		if (t == null | t.getIndicesEstadosEntrada().isEmpty())
			try { currState = new DTState("dead", false, false, true, false);
			} catch (Exception e) {}
		else		
			currState = (DTState) getEstado(t.getIndicesEstadosEntrada().get(0));
		
		return currState;
	}
	
	public DTState getCurrState() throws OperarMecanismoException {
		if (currState == null)
			throw new OperarMecanismoException("There is not a current state");
			
		return currState;
	}
}
