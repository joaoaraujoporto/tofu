package io.tofu.teprl.machines.af.dt;

import java.util.ArrayList;
import java.util.LinkedList;

import io.tofu.teprl.machines.af.AF;
import io.tofu.teprl.machines.af.State;
import io.tofu.teprl.machines.af.Transition;
import io.tofu.teprl.machines.af.TransitionAF;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;
import io.tofu.teprl.machines.exceptions.OperarMecanismoException;

public class DT extends AF {
	private DTState currState;
	private LinkedList<Transition<DTState,String>> transitionsByOther;
	
	public DT(String name) {
		super(name);
		transitionsByOther = new LinkedList<Transition<DTState,String>>();
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
	
	public void addTransitionByOther(String statinName, String statoutName) {
		DTState statout = (DTState) getEstado(statinName);
		DTState statin = (DTState) getEstado(statoutName);
		
		addTransitionByOther(statout, statin);
	}
	
	private void addTransitionByOther(DTState statout, DTState statin) {
		ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
		
		for (Transition<DTState,String> t : transitionsByOther)
			if (t.getStatout().equals(statout))
				if (t.getStatin().equals(statin))
					return; // TODO - add a transition that already exists should not have effect?
		
		transitionsByOther.addLast(new Transition<DTState,String>(statout,
				null, statin));		
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
		
		if (!symbols.contains(String.valueOf(c)))
			for (Transition<DTState,String> t : transitionsByOther)
				if (t.getStatout().equals(currState))
					return (currState = t.getStatin());
		
		TransitionAF t = getTransicao(currState, String.valueOf(c));
		
		if (t == null || t.getIndicesEstadosEntrada().isEmpty())
			try { currState = new DTState("dead", false, false, true, false);
			} catch (Exception e) {System.err.println(e.getMessage());}
		else		
			currState = (DTState) getEstado(t.getIndicesEstadosEntrada().get(0));
		
		return currState;
	}
	
	public DTState getCurrState() throws OperarMecanismoException {
		if (currState == null)
			throw new OperarMecanismoException("There is not a current state");
			
		return currState;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
