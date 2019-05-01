package io.tofu.teprl;

import java.util.HashMap;
import java.util.LinkedList;

import io.tofu.teprl.machines.Machine;
import io.tofu.teprl.machines.af.AF;

public class Teprl {
	private LinkedList<Machine> mecanismos;
	private HashMap<String,Machine> machines;
	private Machine currMachine;

	public Teprl() {
		mecanismos = new LinkedList<Machine>();
		machines = new HashMap<String,Machine>();
		currMachine = null;
	}
	
	/**
	 * 
	 * @param idAF
	 */
	public void criarEstadoAF(Integer indiceAF) {
		AF af = (AF) mecanismos.get(indiceAF);
		af.criarEstado();
	}

	public LinkedList<Machine> getMecanismos() {
		return mecanismos;
	}

	public void setCurrMachine(Machine currMachine) {
		this.currMachine = currMachine;
	}
	
	public void alterarMecanismoAtivo(int indiceMecanismo) {
		currMachine = mecanismos.get(indiceMecanismo);
	}

	public void criarAF(String nome) {
		criarMecanismo(new AF(nome));
	}
	
	public void criarMecanismo(Machine mecanismo) {
		mecanismos.addLast(mecanismo);
		setCurrMachine(mecanismo);
	}

	public void fecharMecanismoAtual() {
		if (mecanismos.isEmpty()) // TODO tratar adequadamente
			return;
		
		int indiceMecanismoAtivo = mecanismos.indexOf(currMachine);
		
		if (indiceMecanismoAtivo == 0 && mecanismos.size() != 1)
			alterarMecanismoAtivo(indiceMecanismoAtivo + 1);
		else if (mecanismos.size() != 1)
			alterarMecanismoAtivo(indiceMecanismoAtivo - 1);
		else
			setCurrMachine(null);
		
		mecanismos.remove(indiceMecanismoAtivo);
	}

	public Machine getMecanismoAtivo() {
		return currMachine;
	}
}

