package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;
import org.junit.Test;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;

public class FirstTest {

	@Test
	public void test() {
		Terminal<String,String> epsilon = new Terminal<String,String>("epsilon");
		
		GLC<String,String> g = new GLC<String,String>("G", new NonTerminal<String,String>("S"),
				epsilon);
		
		ArrayList<Symbol<String,String>> body = new ArrayList<Symbol<String,String>>();
		
		body.add(new NonTerminal<String,String>("A"));
		body.add(new Terminal<String,String>("b"));
		
		Production<String,String> p = new Production<String,String>(
				new NonTerminal<String,String>("S"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(new NonTerminal<String,String>("A"));
		body.add(new NonTerminal<String,String>("B"));
		body.add(new Terminal<String,String>("c"));
		
		p = new Production<String,String>(new NonTerminal<String,String>("S"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(new Terminal<String,String>("b"));
		body.add(new NonTerminal<String,String>("B"));
		
		p = new Production<String,String>(new NonTerminal<String,String>("B"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(new NonTerminal<String,String>("A"));
		body.add(new Terminal<String,String>("d"));
		
		p = new Production<String,String>(new NonTerminal<String,String>("B"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(epsilon);
		
		p = new Production<String,String>(new NonTerminal<String,String>("B"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(new Terminal<String,String>("a"));
		body.add(new NonTerminal<String,String>("A"));
		
		p = new Production<String,String>(new NonTerminal<String,String>("A"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(epsilon);
		
		p = new Production<String,String>(new NonTerminal<String,String>("A"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(new NonTerminal<String,String>("A"));
		
		p = new Production<String,String>(new NonTerminal<String,String>("S"), body);
		g.addProduction(p);
		
		g.updateFirst();
		System.out.println(g.getFirst(new NonTerminal<String,String>("S")));
		System.out.println(g.getFirst(new NonTerminal<String,String>("A")));
		System.out.println(g.getFirst(new NonTerminal<String,String>("B")));
	}

}
