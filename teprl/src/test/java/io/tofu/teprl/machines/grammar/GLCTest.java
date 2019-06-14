package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;

import org.junit.Test;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;

public class GLCTest {
	@Test
	public void test() {
		Terminal<String,String> epsilon = new Terminal<String,String>("epsilon");
		GLC<String,String> g = new GLC<String,String>("G", new NonTerminal<String,String>("PROGRAM"), epsilon);
		
		ArrayList<Symbol<String,String>> body = new ArrayList<Symbol<String,String>>();
		
		body.add((Symbol<String,String>) AuxSym.LEFTPAR);
		body.add(new Symbol<String,String>("CLASSLIST"));
		body.add((Symbol<String,String>) AuxSym.RIGHTPAR);
		body.add((Symbol<String,String>) AuxSym.POSSIBLE);
		
		Production<String,String> p = new Production<String,String>(
				new NonTerminal<String,String>("PROGRAM"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add((Symbol<String,String>) AuxSym.LEFTPAR);
		body.add(new Symbol<String,String>("CLASSDECL"));
		body.add((Symbol<String,String>) AuxSym.RIGHTPAR);
		body.add((Symbol<String,String>) AuxSym.PLUS);
		
		p = new Production<String,String>(new NonTerminal<String,String>("CLASSLIST"), body);
		g.addProduction(p);
		
		body = new ArrayList<Symbol<String,String>>();
		
		body.add(new Symbol<String,String>("class"));
		body.add(new Symbol<String,String>("ident"));
		body.add((Symbol<String,String>) AuxSym.LEFTPAR);
		body.add(new Symbol<String,String>("extends"));
		body.add((Symbol<String,String>) AuxSym.RIGHTPAR);
		body.add((Symbol<String,String>) AuxSym.POSSIBLE);
		body.add(new Symbol<String,String>("CLASSBODY"));
		
		p = new Production<String,String>(new NonTerminal<String,String>("CLASSDECL"), body);
		g.addProduction(p);
		
		body.add(new Symbol<String,String>("{"));
		body.add((Symbol<String,String>) AuxSym.LEFTPAR);
		body.add(new Symbol<String,String>("CLASSLIST"));
		body.add((Symbol<String,String>) AuxSym.RIGHTPAR);
		body.add((Symbol<String,String>) AuxSym.POSSIBLE);
		body.add((Symbol<String,String>) AuxSym.LEFTPAR);
		body.add(new Symbol<String,String>("VARDECL"));
		body.add(new Symbol<String,String>("VARDECL;"));
		body.add((Symbol<String,String>) AuxSym.RIGHTPAR);
		body.add((Symbol<String,String>) AuxSym.POSSIBLE);
		
		p = new Production<String,String>(new NonTerminal<String,String>("CLASSDECL"), body);
		g.addProduction(p);
		
		System.out.println(g.getProductions().toString());
	}
}
