package io.tofu.teprl.machines.af;

import io.tofu.teprl.machines.exceptions.EditarMecanismoException;

public class State {
	private String name;
	private boolean start;
	private boolean accept;
	private boolean dead;

	public State(String name) throws EditarMecanismoException {
		this(name, false, false, false);
	}
	
	public State(String name, Boolean start, Boolean accept, Boolean dead) throws EditarMecanismoException {
		if (accept && dead)
			throw new EditarMecanismoException("A state cannot accept and be dead");
		
		this.name = name;
		this.start = start;
		this.accept = accept;
		this.setDead(dead);
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean inicial) {
		this.start = inicial;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean aceitacao) {
		this.accept = aceitacao;
	}

	public void setName(String nome) {
		this.name = nome;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.hashCode() == ((State) o).hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}
}

