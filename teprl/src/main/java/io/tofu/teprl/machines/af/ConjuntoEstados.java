package io.tofu.teprl.machines.af;

import java.util.ArrayList;

public class ConjuntoEstados extends ArrayList<State> {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	public ConjuntoEstados() {
		nome = toString();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		String nomeConjuntoEstados = "{";
		
		if (!isEmpty()) {
			nomeConjuntoEstados += get(0).getName();
			
			for (int i = 1; i < size(); i++)
				nomeConjuntoEstados += ", " + get(i).getName();
		}
		
		return nomeConjuntoEstados + "}";
	}
	
	@Override
	public boolean equals(Object o) {
		ConjuntoEstados outro = (ConjuntoEstados) o;
		
		boolean c1 = this.containsAll(outro);
		boolean c2 = outro.containsAll(this);
		
		return c1 && c2;
	}
}

