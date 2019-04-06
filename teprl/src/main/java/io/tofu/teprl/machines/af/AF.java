package io.tofu.teprl.machines.af;

import java.util.ArrayList;
import java.util.LinkedList;

import io.tofu.teprl.machines.Machine;
import io.tofu.teprl.machines.af.dt.DTState;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;

public class AF extends Machine {
	private static int order;
	private LinkedList<State> states;
	private LinkedList<String> symbols;
	private LinkedList<Transition> transitions;
	
	public AF(String nome) {
		super(nome);
		order = 0;
		states = new LinkedList<State>();
		symbols = new LinkedList<String>();
		transitions = new LinkedList<Transition>();
		// try { estadoMorto = new State("-"); } catch (Exception e) {}
	}

	public void criarEstado() {
		String nomeEstado = "q" + states.size();
		
		for (int i = 1; existeEstadoComNome(nomeEstado); i++)
			nomeEstado = "q" + (states.size() + i);

		try {
			State estado;
			estado = new State(nomeEstado); 
			
			states.addLast(estado);
			order++;
			
			if (order == 1)
				estado.setStart(true);
			
			for (String simbolo : symbols) {
				ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
				
				transitions.addLast(new Transition(states.indexOf(estado),
						symbols.indexOf(simbolo),
						indicesEstadosEntrada));
			}
		} catch (Exception e) {}
	}

	public void createState(String name) throws EditarMecanismoException {
		State s = new State(name);
		addState(s);
	}
	
	public void addState(State s) throws EditarMecanismoException {
		if (existeEstadoComNome(s.getName()))
			throw new EditarMecanismoException("There is already a state with this name");
		
		states.addLast(s);
		order++;
		
		if (order == 1)
			s.setStart(true); // TODO - I wonder if is needed
		
		for (String simbolo : symbols) {
			ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
			
			transitions.addLast(new Transition(states.indexOf(s),
					symbols.indexOf(simbolo),
					indicesEstadosEntrada));
		}
	}
	
	public void addTransition(String nomeEstadoSaida, String symbol, String nomeEstadoEntrada) {
		State estadoSaida = getEstado(nomeEstadoSaida);
		State estadoEntrada = getEstado(nomeEstadoEntrada);
		
		addTransition(estadoSaida, symbol, estadoEntrada);
	}
	
	public void editTransition(String stateOutName, String symbol, String stateInName) {
		
	}
	
	public void getTransition() {
		
	}
	
	public void addTransition(String nomeEstadoSaida, ArrayList<String> symbols, String nomeEstadoEntrada) {
		State estadoSaida = getEstado(nomeEstadoSaida);
		State estadoEntrada = getEstado(nomeEstadoEntrada);
		
		for (String s : symbols)
			addTransition(estadoSaida, s, estadoEntrada);
	}
	
	public void addTransition(State estadoSaida, String symbol, State estadoEntrada) {
		ArrayList<Integer> indicesEstadosEntrada = new ArrayList<Integer>();
		
		for (Transition t : transitions)
			if (t.getIndiceEstadoSaida() == getIndiceEstado(estadoSaida))
				if (t.getIndiceSimbolo() == symbols.indexOf(symbol)) {
					t.getIndicesEstadosEntrada().add(getIndiceEstado(estadoEntrada));
					return;
				}
		
		if (!estadoEntrada.isDead())
			indicesEstadosEntrada.add(getIndiceEstado(estadoEntrada));
		
		transitions.addLast(new Transition(getIndiceEstado(estadoSaida),
				symbols.indexOf(symbol),
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
		estado.setName(nome);
		
		if (existeEstadoComNome(estado)) {
			estado.setName(nomeAntigo);
			throw new EditarMecanismoException("Já existe estado com esse nome");
		}
		
		estado.setName(nome);
		estado.setStart(inicial);
		estado.setAccept(aceitacao);
		
		if (getIndiceEstado(estado) == 0)
			estado.setStart(true);
	}

	private void alterarEstado(String nome, Boolean aceitacao) {
		State estado = null;
		
		for (State e : states)
			if (e.getName().equals(nome))
					estado = e;
		
		if (estado == null)
			return;
		
		estado.setAccept(aceitacao);	
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
		Transition transicao = transitions.get(indiceTransicao);
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
		
		if (!estadoEntrada.isDead())
			indicesEstadosEntrada.add(getIndiceEstado(estadoEntrada));
			
		Transition t = getTransicao(estadoSaida, simbolo);
		t.setIndiceEstadoSaida(getIndiceEstado(estadoSaida));
		t.setIndiceSimbolo(symbols.indexOf(simbolo));
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
	 * @param symbol
	 * @throws EditarMecanismoException 
	 */
	public void addSymbol(String symbol) throws EditarMecanismoException {
		for (String s : symbols)
			if (s.equals(symbol))
				throw new EditarMecanismoException("The symbol "+ s +" already belongs to the alphabet of AF");
		
		symbols.add(symbol);
		
		for (State state : states)
			transitions.addLast(new Transition(states.indexOf(state),
					symbols.indexOf(symbol), new ArrayList<Integer>()));
	}
	
	public void addSymbols(ArrayList<String> symbols) throws EditarMecanismoException {
		for (String s : symbols)
			addSymbol(s);
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
		if (symbols.contains(simbolo))
			throw new EditarMecanismoException("Símbolo já pertence ao alfabeto");
		
		symbols.add(indiceSimbolo, simbolo);
		symbols.remove(indiceSimbolo+1);
	}

	public void excluirEstado(String nomeEstado) throws EditarMecanismoException {
		int i;
		
		for (i = 0; i < states.size(); i++)
			if (states.get(i).getName().equals(nomeEstado)) {
				Integer indiceEstado = states.indexOf(states.get(i));
				
				ArrayList<Integer> indiceTransicoesRemocao = new ArrayList<Integer>();
				
				for (Transition transicao : transitions)
					if (transicao.getIndiceEstadoSaida().equals(indiceEstado))
						indiceTransicoesRemocao.add(transitions.indexOf(transicao));
				
				for(Integer indiceTransicao : indiceTransicoesRemocao)
					transitions.remove(transitions.get(indiceTransicao));
				
				for (Transition transicao : transitions)
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
		
		for (i = 0; i < symbols.size(); i++)
			if (symbols.get(i).equals(simbolo)) {
				Integer indiceSimbolo = symbols.indexOf(simbolo);
				
				ArrayList<Integer> indiceTransicoesRemocao = new ArrayList<Integer>();
				
				for (Transition transicao : transitions)
					if (transicao.getIndiceSimbolo().equals(indiceSimbolo))
						indiceTransicoesRemocao.add(transitions.indexOf(transicao));
				
				for(Integer indiceTransicao : indiceTransicoesRemocao)
					transitions.remove((int) indiceTransicao);
				
				break;
			}
		
		if (i == symbols.size())
			throw new EditarMecanismoException("Símbolo não pertence ao alfabeto");
		
		symbols.remove(i);
	}
 
	private ConjuntoEstados getEstadosEntrada(String nomeEstado, String simbolo) {
		ConjuntoEstados estadosEntrada = new ConjuntoEstados();
		
		State estadoSaida = getEstado(nomeEstado);
		Transition transicao = getTransicao(estadoSaida, simbolo);
		
		for (Integer indiceEstado : transicao.getIndicesEstadosEntrada())
			estadosEntrada.add(states.get(indiceEstado));
		
		return estadosEntrada;
	}
	
	// TODO - soh para AFDs
	private State getEstadoEntrada(String nomeEstadoSaida, String simbolo) {
		Transition t = getTransicao(getEstado(nomeEstadoSaida), simbolo);
		
		if (t.getIndicesEstadosEntrada().isEmpty())
			try { return new State("dead", false, false, true);
			} catch (Exception e) {}
		
		return getEstado(t.getIndicesEstadosEntrada().get(0));
	}
	
	public Transition getTransicao(State estadoSaida, String simbolo) {
		return getTransicao(states.indexOf(estadoSaida), symbols.indexOf(simbolo));
	}
	
	public Transition getTransicao(Integer indiceEstadoSaida, String simbolo) {
		return getTransicao(indiceEstadoSaida, symbols.indexOf(simbolo));
	}
	
	public Transition getTransicao(Integer indiceEstadoSaida, Integer indiceSimbolo) {
		for (Transition transicao : transitions)
			if (transicao.getIndiceEstadoSaida().equals(indiceEstadoSaida))
				if (transicao.getIndiceSimbolo().equals(indiceSimbolo))
					return transicao;
		
		return null;
	}

	public void adicionarEstadoEntradaTransicao(String nomeEstadoSaida, String simbolo, String nomeEstadoEntrada) {
		State eS = getEstado(nomeEstadoSaida);
		State eE = getEstado(nomeEstadoEntrada);
		Transition t = getTransicao(eS, simbolo);
		
		t.getIndicesEstadosEntrada().add(getIndiceEstado(eE));
	}

	private Integer getIndiceEstado(State eE) {
		return states.indexOf(eE);
	}

	private State getEstado(String stateName) {
		if (stateName.equals("-"))
			try { return new State("dead", false, false, true);
			} catch (Exception e) {}
		
		for (State e : states)
			if (e.getName().equals(stateName))
				return e;
		
		return null;
	}

	public State getEstado(Integer indiceEstado) {
		return states.get(indiceEstado);
	}
	
	private ConjuntoEstados getEstadosFinais() {
		ConjuntoEstados finais = new ConjuntoEstados();
		
		for (State e : states)
			if (e.isAccept())
				finais.add(e);
		
		return finais;
	}
	
	private ConjuntoEstados getEstadosNaoFinais() {
		ConjuntoEstados naoFinais = new ConjuntoEstados();
		
		for (State e : states)
			if (!e.isAccept())
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
		if (estado.isAccept())
			return false;
		
		if (!visitados.contains(estado)) {
			visitados.add(estado);
			
			for (Transition t : transitions)
				if (t.getIndiceEstadoSaida().equals(getIndiceEstado(estado)))
					for (Integer indiceEstadoEntrada : t.getIndicesEstadosEntrada()) // somente um indice, pois AFD
						if (!ehMorto(states.get(indiceEstadoEntrada), visitados))
							return false;
		}
		
		return true;
	}
	
	public State getInitialState() {
		for (State e : states)
			if (e.isStart())
				return e;
		
		return null;
	}
}

