package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;
import org.junit.Test;

public class FirstTest {

	@Test
	public void test() {
		GLC g = new GLC("G", new NonTerminal("S"));
		
		ArrayList<Symbol> body = new ArrayList<Symbol>();
		
		body.add(new NonTerminal("A"));
		body.add(new Terminal("b"));
		
		Production p = new Production(new NonTerminal("S"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(new NonTerminal("A"));
		body.add(new NonTerminal("B"));
		body.add(new Terminal("c"));
		
		p = new Production(new NonTerminal("S"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(new Terminal("b"));
		body.add(new NonTerminal("B"));
		
		p = new Production(new NonTerminal("B"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(new NonTerminal("A"));
		body.add(new Terminal("d"));
		
		p = new Production(new NonTerminal("B"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(Grammar.EPSILON);
		
		p = new Production(new NonTerminal("B"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(new Terminal("a"));
		body.add(new NonTerminal("A"));
		
		p = new Production(new NonTerminal("A"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(Grammar.EPSILON);
		
		p = new Production(new NonTerminal("A"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(new NonTerminal("A"));
		
		p = new Production(new NonTerminal("S"), body);
		g.addProduction(p);
		
		g.updateFirst();
		System.out.println(g.getFirst(new NonTerminal("S")));
		System.out.println(g.getFirst(new NonTerminal("A")));
		System.out.println(g.getFirst(new NonTerminal("B")));
	}

}
