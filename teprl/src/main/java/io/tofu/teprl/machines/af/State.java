package io.tofu.teprl.machines.af;

public class State {
	private int numero;
	private String nome;
	private boolean inicial;
	private boolean aceitacao;

	public State(Integer numero, String nome) {
		this.numero = numero;
		this.nome = nome;
	}
	
	public State(Integer numero, String nome, Boolean inicial, Boolean aceitacao) {
		this.numero = numero;
		this.nome = nome;
		this.inicial = inicial;
		this.aceitacao = aceitacao;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	@Override
	public int hashCode() {
		return numero;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
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
		return this.numero == ((State) o).getNumero();
	}
	
	@Override
	public String toString() {
		return nome;
	}
}

