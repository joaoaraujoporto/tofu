package io.tofu.teprl.machines.af;

import java.util.ArrayList;
import java.util.LinkedList;

import io.tofu.teprl.machines.Machine;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;

public class AF extends Machine {
	private static int order;
	private LinkedList<State> states;
	private LinkedList<String> simbolos;
	private LinkedList<Transicao> transicoes;
	private State estadoMorto;
	
	public AF(String nome) {
		super(nome);
		order = 0;
		states = new LinkedList<State>();
		simbolos = new LinkedList<String>();
		transicoes = new LinkedList<Transicao>();
		estadoMorto = new State("-");
	}

	public void criarEstado() {
		String nomeEstado = "q" + states.size();
		
		for (int i = 1; existeEstadoComNome(nomeEstado); i++)
			nomeEstado = "q" + (states.size() + i);
		
		State estado = new State(nomeEstado);
		
		if (order == 1)
			estado.setInicial(true);
		
		states.addLast(estado);
		
		for (String simbolo : simbolos) {
			ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
			
			transicoes.addLast(new Transicao(states.indexOf(estado),
					simbolos.indexOf(simbolo),
					indicesEstadosEntrada));
		}
	}

	public void createState(String name) throws EditarMecanismoException {
		State s = new State(name);
		addState(s);
	}
	
	public void addState(State s) throws EditarMecanismoException {
		if (existeEstadoComNome(s.getName()))
			throw new EditarMecanismoException("There is already a state with this name");
		
		states.addLast(s);
		
		if (order == 1)
			s.setInicial(true);
		
		for (String simbolo : simbolos) {
			ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
			
			transicoes.addLast(new Transicao(states.indexOf(s),
					simbolos.indexOf(simbolo),
					indicesEstadosEntrada));
		}
	}
	
	private void inserirEstado(State estado) {
		// TODO - encurtar criarEstado com esse metodo
		if (existeEstadoComNome(estado.getName()))
			return;
		
		states.addLast(estado);
		
		for (String simbolo : simbolos) {
			ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
			
			transicoes.addLast(new Transicao(states.indexOf(estado),
					simbolos.indexOf(simbolo),
					(ArrayList<Integer>)indicesEstadosEntrada));
		}
	}
	
	private void inserirTransicao(String nomeEstadoSaida, String simbolo, String nomeEstadoEntrada) {
		State estadoSaida = getEstado(nomeEstadoSaida);
		State estadoEntrada = getEstado(nomeEstadoEntrada);
		ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
		
		if (!estadoEntrada.equals(estadoMorto))
			indicesEstadosEntrada.add(getIndiceEstado(estadoEntrada));
		
		transicoes.addLast(new Transicao(getIndiceEstado(estadoSaida),
				simbolos.indexOf(simbolo),
				(ArrayList<Integer>)indicesEstadosEntrada));
	}
	
	public void inserirTransicao(State estadoSaida, String simbolo, State estadoEntrada) {
		ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
		
		if (!estadoEntrada.equals(estadoMorto))
			indicesEstadosEntrada.add(getIndiceEstado(estadoEntrada));
		
		transicoes.addLast(new Transicao(getIndiceEstado(estadoSaida),
				simbolos.indexOf(simbolo),
				(ArrayList<Integer>)indicesEstadosEntrada));
	}
	

	public Object criarImagemAF() {
		// TODO - implement AF.criarImagemAF
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param nome
	 * @param indiceEstado 
	 * @param inicial
	 * @param aceitacao
	 * @throws EditarMecanismoException 
	 */
	public void alterarEstado(Integer indiceEstado, String nome,
			boolean inicial, boolean aceitacao) throws EditarMecanismoException {
		State estado = states.get(indiceEstado);
		String nomeAntigo = estado.getName();
		estado.setNome(nome);
		
		if (existeEstadoComNome(estado)) {
			estado.setNome(nomeAntigo);
			throw new EditarMecanismoException("Já existe estado com esse nome");
		}
		
		estado.setNome(nome);
		estado.setInicial(inicial);
		estado.setAceitacao(aceitacao);
		
		if (getIndiceEstado(estado) == 0)
			estado.setInicial(true);
	}

	private void alterarEstado(String nome, Boolean aceitacao) {
		State estado = null;
		
		for (State e : states)
			if (e.getName().equals(nome))
					estado = e;
		
		if (estado == null)
			return;
		
		estado.setAceitacao(aceitacao);	
	}
	
	public Boolean existeEstadoComNome(String nome) {
		for (State e : states)
			if (e.getName().equals(nome))
					return true;
		
		return false;
	}
	
	public Boolean existeEstadoComNome(State estado) {
		for (State e : states)
			if (!e.equals(estado))
				if (e.getName().equals(estado.getName()))
					return true;
		
		return false;
	}
	
	/**
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		// TODO - implement AF.setNome
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param inicial
	 */
	public void setInicial(boolean inicial) {
		// TODO - implement AF.setInicial
		throw new UnsupportedOperationException();
	}

	public boolean existeInicial() {
		// TODO - implement AF.existeInicial
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param aceitacao
	 */
	public void setFinal(boolean aceitacao) {
		// TODO - implement AF.setFinal
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idEstadoDeSaida
	 * @param nomeEstadoDeEntrada
	 * @throws EditarMecanismoException 
	 */
	public void alterarTransicao(Integer indiceTransicao, String estadosEntrada) throws EditarMecanismoException {		
		Transicao transicao = transicoes.get(indiceTransicao);
		String[] nomesEstados = estadosEntrada.split(",");
		
		for (int i = 0; i < nomesEstados.length; i++)
			if (nomesEstados[i].startsWith(" "))
				nomesEstados[i] = nomesEstados[i].substring(1);
		
		ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
		
		for (int i = 0; i < nomesEstados.length; i++) {
			
			int j;
			for (j = 0; j < states.size(); j++)
				if (states.get(j).getName().equals(nomesEstados[i])) {
					indicesEstadosEntrada.add(states.indexOf(states.get(j)));
					break;
				}
			
			if (j == states.size()) {
				if (!nomesEstados[i].equals("-"))
					throw new EditarMecanismoException("Um ou mais dos estados indicados não existe");
			}	
		}
			
		transicao.getIndicesEstadosEntrada().clear();
		transicao.getIndicesEstadosEntrada().addAll(indicesEstadosEntrada);
	}
	
	private void alterarTransicao(String nomeEstadoSaida, String simbolo, String nomeEstadoEntrada) {
		State estadoSaida = getEstado(nomeEstadoSaida);
		State estadoEntrada = getEstado(nomeEstadoEntrada);
		
		ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
		
		if (!estadoMorto.equals(estadoEntrada))
			indicesEstadosEntrada.add(getIndiceEstado(estadoEntrada));
			
		Transicao t = getTransicao(estadoSaida, simbolo);
		t.setIndiceEstadoSaida(getIndiceEstado(estadoSaida));
		t.setIndiceSimbolo(simbolos.indexOf(simbolo));
		t.setIndicesEstadosEntrada(indicesEstadosEntrada);
	}

	/**
	 * 
	 * @param nomeEstado
	 */
	public Object getIdEstado(String nomeEstado) {
		// TODO - implement AF.getIdEstado
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idEstado
	 */
	public Object excluirEstado(Object idEstado) {
		// TODO - implement AF.excluirEstado
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idEstado
	 */
	public Object getTransicoesEstadoDeSaida(Object idEstado) {
		// TODO - implement AF.getTransicoesEstadoDeSaida
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idEstado
	 */
	public Object getTransicoesEstadoDeEntrada(Object idEstado) {
		// TODO - implement AF.getTransicoesEstadoDeEntrada
		throw new UnsupportedOperationException();
	}

	public int getQtdEstados() {
		return states.size();
	}

	/**
	 * 
	 * @param simbolo
	 * @throws EditarMecanismoException 
	 */
	public void adicionarSimbolo(String simbolo) throws EditarMecanismoException {
		for (String s : simbolos)
			if (s.equals(simbolo))
				throw new EditarMecanismoException("Símbolo já pertence ao alfabeto");
		
		simbolos.add(simbolo);
		
		for (State estado : states)
			transicoes.addLast(new Transicao(states.indexOf(estado),
					simbolos.indexOf(simbolo), new ArrayList<Integer>()));
	}

	public Object excluirSimbolo() {
		// TODO - implement AF.excluirSimbolo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param simbolo
	 */
	public Object getTransicoesComSimbolo(Object simbolo) {
		// TODO - implement AF.getTransicoesComSimbolo
		throw new UnsupportedOperationException();
	}

	public void alterarSimbolo(Integer indiceSimbolo, String simbolo) throws EditarMecanismoException {
		if (simbolos.contains(simbolo))
			throw new EditarMecanismoException("Símbolo já pertence ao alfabeto");
		
		simbolos.add(indiceSimbolo, simbolo);
		simbolos.remove(indiceSimbolo+1);
	}

	public void excluirEstado(String nomeEstado) throws EditarMecanismoException {
		int i;
		
		for (i = 0; i < states.size(); i++)
			if (states.get(i).getName().equals(nomeEstado)) {
				Integer indiceEstado = states.indexOf(states.get(i));
				
				ArrayList<Integer> indiceTransicoesRemocao = new ArrayList<Integer>();
				
				for (Transicao transicao : transicoes)
					if (transicao.getIndiceEstadoSaida().equals(indiceEstado))
						indiceTransicoesRemocao.add(transicoes.indexOf(transicao));
				
				for(Integer indiceTransicao : indiceTransicoesRemocao)
					transicoes.remove(transicoes.get(indiceTransicao));
				
				for (Transicao transicao : transicoes)
					if (transicao.getIndicesEstadosEntrada().contains(indiceEstado))
						transicao.getIndicesEstadosEntrada().remove(indiceEstado);
				
				break;
			}
		
		if (i == states.size())
			throw new EditarMecanismoException("Estado não existe");
		
		states.remove(i);
	}

	public void excluirSimbolo(String simbolo) throws EditarMecanismoException {
		int i;
		
		for (i = 0; i < simbolos.size(); i++)
			if (simbolos.get(i).equals(simbolo)) {
				Integer indiceSimbolo = simbolos.indexOf(simbolo);
				
				ArrayList<Integer> indiceTransicoesRemocao = new ArrayList<Integer>();
				
				for (Transicao transicao : transicoes)
					if (transicao.getIndiceSimbolo().equals(indiceSimbolo))
						indiceTransicoesRemocao.add(transicoes.indexOf(transicao));
				
				for(Integer indiceTransicao : indiceTransicoesRemocao)
					transicoes.remove((int) indiceTransicao);
				
				break;
			}
		
		if (i == simbolos.size())
			throw new EditarMecanismoException("Símbolo não pertence ao alfabeto");
		
		simbolos.remove(i);
	}
 
	private ConjuntoEstados getEstadosEntrada(String nomeEstado, String simbolo) {
		ConjuntoEstados estadosEntrada = new ConjuntoEstados();
		
		State estadoSaida = getEstado(nomeEstado);
		Transicao transicao = getTransicao(estadoSaida, simbolo);
		
		for (Integer indiceEstado : transicao.getIndicesEstadosEntrada())
			estadosEntrada.add(states.get(indiceEstado));
		
		return estadosEntrada;
	}
	
	// TODO - soh para AFDs
	private State getEstadoEntrada(String nomeEstadoSaida, String simbolo) {
		Transicao t = getTransicao(getEstado(nomeEstadoSaida), simbolo);
		
		if (t.getIndicesEstadosEntrada().isEmpty())
			return estadoMorto;
		
		return getEstado(t.getIndicesEstadosEntrada().get(0));
	}
	
	public Transicao getTransicao(State estadoSaida, String simbolo) {
		return getTransicao(states.indexOf(estadoSaida), simbolos.indexOf(simbolo));
	}
	
	public Transicao getTransicao(Integer indiceEstadoSaida, String simbolo) {
		return getTransicao(indiceEstadoSaida, simbolos.indexOf(simbolo));
	}
	
	public Transicao getTransicao(Integer indiceEstadoSaida, Integer indiceSimbolo) {
		for (Transicao transicao : transicoes)
			if (transicao.getIndiceEstadoSaida().equals(indiceEstadoSaida))
				if (transicao.getIndiceSimbolo().equals(indiceSimbolo))
					return transicao;
		
		return null;
	}

	public void adicionarEstadoEntradaTransicao(String nomeEstadoSaida, String simbolo, String nomeEstadoEntrada) {
		State eS = getEstado(nomeEstadoSaida);
		State eE = getEstado(nomeEstadoEntrada);
		Transicao t = getTransicao(eS, simbolo);
		
		t.getIndicesEstadosEntrada().add(getIndiceEstado(eE));
	}

	private Integer getIndiceEstado(State eE) {
		return states.indexOf(eE);
	}

	private State getEstado(String nomeEstado) {
		if (nomeEstado.equals("-"))
			return estadoMorto;
		
		for (State e : states)
			if (e.getName().equals(nomeEstado))
				return e;
		
		return null;
	}

	public State getEstado(Integer indiceEstado) {
		return states.get(indiceEstado);
	}
	
	private ConjuntoEstados getEstadosFinais() {
		ConjuntoEstados finais = new ConjuntoEstados();
		
		for (State e : states)
			if (e.isAceitacao())
				finais.add(e);
		
		return finais;
	}
	
	private ConjuntoEstados getEstadosNaoFinais() {
		ConjuntoEstados naoFinais = new ConjuntoEstados();
		
		for (State e : states)
			if (!e.isAceitacao())
				naoFinais.add(e);
		
		return naoFinais;
	}
	
	private ConjuntoEstados getEstadosMortos() {
		ConjuntoEstados mortos = new ConjuntoEstados();
		
		for (State e : states)
			if (ehMorto(e))
				mortos.add(e);
		
		return mortos;
	}
	
	private boolean ehMorto(State estado) {
		ArrayList<State> visitados = new ArrayList<State>();
		return ehMorto(estado, visitados);
	}

	private boolean ehMorto(State estado, ArrayList<State> visitados) {
		if (estado.isAceitacao())
			return false;
		
		if (!visitados.contains(estado)) {
			visitados.add(estado);
			
			for (Transicao t : transicoes)
				if (t.getIndiceEstadoSaida().equals(getIndiceEstado(estado)))
					for (Integer indiceEstadoEntrada : t.getIndicesEstadosEntrada()) // somente um indice, pois AFD
						if (!ehMorto(states.get(indiceEstadoEntrada), visitados))
							return false;
		}
		
		return true;
	}
	
	public State getInitialState() {
		for (State e : states)
			if (e.isInicial())
				return e;
		
		return null;
	}
}

