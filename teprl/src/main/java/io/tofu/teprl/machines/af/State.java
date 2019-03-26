package io.tofu.teprl.machines.af;

public class State {
	private String nome;
	private boolean inicial;
	private boolean aceitacao;

	public State(String nome) {
		this.nome = nome;
	}
	
	public State(String name, Boolean inicial, Boolean aceitacao) {
		this(name);
		this.inicial = inicial;
		this.aceitacao = aceitacao;
	}
	
	public String getName() {
		return this.nome;
	}
	
	@Override
	public int hashCode() {
		return nome.hashCode();
	}

	public boolean isInicial() {
		return inicial;
	}

	public void setInicial(boolean inicial) {
		this.inicial = inicial;
	}

	public boolean isAceitacao() {
		return aceitacao;
	}

	public void setAceitacao(boolean aceitacao) {
		this.aceitacao = aceitacao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.hashCode() == ((State) o).hashCode();
	}
	
	@Override
	public String toString() {
		return nome;
	}
}

