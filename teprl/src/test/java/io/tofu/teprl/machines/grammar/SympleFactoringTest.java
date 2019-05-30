package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;
import org.junit.Test;

public class SympleFactoringTest {

	@Test
	public void test() {
		G g = new G();
		
		ArrayList<Symbol> body = new ArrayList<Symbol>();
		
		body.add(new Terminal("a"));
		body.add(new Terminal("b"));
		body.add(new NonTerminal("A"));
		body.add(new AuxSym("|"));
		body.add(new Terminal("a"));
		body.add(new Terminal("b"));
		body.add(new NonTerminal("B"));
		
		Production p = new Production(new NonTerminal("S"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(new Terminal("a"));
		
		p = new Production(new NonTerminal("A"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol>();
		
		body.add(new Terminal("b"));
		
		p = new Production(new NonTerminal("B"), body);
		g.addProduction(p);
		
		System.out.println(g.getProductions().toString());
		Expander.toFactor(g);
		System.out.println(g.getProductions().toString());
	}

}
