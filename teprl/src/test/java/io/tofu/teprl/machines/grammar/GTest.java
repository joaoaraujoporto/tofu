package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;

import org.junit.Test;

public class GTest {
	@Test
	public void test() {
		G g = new G();
		
		ArrayList<Symbol> body = new ArrayList<Symbol>();
		
		body.add(new AuxSym("("));
		body.add(new Symbol("CLASSLIST"));
		body.add(new AuxSym(")"));
		body.add(new AuxSym("?"));
		
		Production p = new Production(new NonTerminal("PROGRAM"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(new AuxSym("("));
		body.add(new Symbol("CLASSDECL"));
		body.add(new AuxSym(")"));
		body.add(new AuxSym("+"));
		
		p = new Production(new NonTerminal("CLASSLIST"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(new Symbol("class"));
		body.add(new Symbol("ident"));
		body.add(new AuxSym("("));
		body.add(new Symbol("extends"));
		body.add(new AuxSym(")"));
		body.add(new AuxSym("?"));
		body.add(new Symbol("CLASSBODY"));
		
		p = new Production(new NonTerminal("CLASSDECL"), body);
		g.addProduction(p);
		
		body.add(new Symbol("{"));
		body.add(new AuxSym("("));
		body.add(new Symbol("CLASSLIST"));
		body.add(new AuxSym(")"));
		body.add(new AuxSym("?"));
		body.add(new AuxSym("("));
		body.add(new Symbol("VARDECL"));
		body.add(new Symbol("VARDECL;"));
		body.add(new AuxSym(")"));
		body.add(new AuxSym("?"));
		
		p = new Production(new NonTerminal("CLASSDECL"), body);
		g.addProduction(p);
		
		System.out.println(g.getProductions().toString());
	}
}
