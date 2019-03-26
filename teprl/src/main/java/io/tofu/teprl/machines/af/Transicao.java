package io.tofu.teprl.machines.af;

import java.util.ArrayList;

public class Transicao implements Cloneable {
	private Integer indiceEstadoSaida;
	private Integer indiceSimbolo;
	private ArrayList<Integer> indicesEstadosEntrada;

	public Transicao(int indiceEstadoSaida, int indiceSimbolo) {
		this.indiceEstadoSaida = indiceEstadoSaida;
		this.indiceSimbolo = indiceSimbolo;
		this.indicesEstadosEntrada = new ArrayList<Integer>();
	}
	
	public Transicao(int indiceEstadoSaida, int indiceSimbolo, ArrayList<Integer> indicesEstadosEntrada) {
		this.indiceEstadoSaida = indiceEstadoSaida;
		this.indiceSimbolo = indiceSimbolo;
		this.indicesEstadosEntrada = indicesEstadosEntrada;
	}
	
	/**
	 * 
	 * @param idEstadoDeEntrada
	 */
	public boolean setEstadoDeEntrado(int idEstadoDeEntrada) {
		// TODO - implement Transicao.setEstadoDeEntrado
		throw new UnsupportedOperationException();
	}

	public void setIndiceEstadoSaida(Integer indiceEstadoSaida) {
		this.indiceEstadoSaida = indiceEstadoSaida;
	}

	public void setIndiceSimbolo(Integer indiceSimbolo) {
		this.indiceSimbolo = indiceSimbolo;
	}

	public void setIndicesEstadosEntrada(ArrayList<Integer> indicesEstadosEntrada) {
		this.indicesEstadosEntrada = indicesEstadosEntrada;
	}

	public Integer getIndiceEstadoSaida() {
		return indiceEstadoSaida;
	}

	public Integer getIndiceSimbolo() {
		return indiceSimbolo;
	}

	public ArrayList<Integer> getIndicesEstadosEntrada() {
		return indicesEstadosEntrada;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
}

