package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;
import org.junit.Test;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;

public class SympleFactoringTest {

	@Test
	public void test() {
		Terminal<String,String> epsilon = new Terminal("epsilon");
		GLC<String,String> g = new GLC<String,String>("G", new NonTerminal<String,String>("S"),
				epsilon);
		
		ArrayList<Symbol<String,String>> body = new ArrayList<Symbol<String,String>>();
		
		body.add(new Terminal<String,String>("a"));
		body.add(new Terminal<String,String>("b"));
		body.add(new NonTerminal<String,String>("A"));
		body.add((Symbol<String,String>) AuxSym.OR);
		body.add(new Terminal<String,String>("a"));
		body.add(new Terminal<String,String>("b"));
		body.add(new NonTerminal<String,String>("B"));
		
		Production<String,String> p = new Production<String,String>(
				new NonTerminal<String,String>("S"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(new Terminal<String,String>("a"));
		
		p = new Production<String,String>(new NonTerminal<String,String>("A"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(new Terminal<String,String>("b"));
		
		p = new Production<String,String>(new NonTerminal<String,String>("B"), body);
		g.addProduction(p);
		
		System.out.println(g.getProductions().toString());
		// Expander.toFactor(g); - TODO retrieve
		System.out.println(g.getProductions().toString());
	}

}
