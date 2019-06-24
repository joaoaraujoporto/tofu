package io.tofu.tepas;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.tofu.commons.symbol.NonTerminal;
import io.tofu.teprl.machines.grammar.Production;
import io.tofu.commons.symbol.Symbol;
import io.tofu.commons.symbol.Terminal;
import io.tofu.commons.symbol.Token;

public class TASHashMapTest {
	
	@Test
	public void test()  {
		Map<TASIndex,Production<String,AttribSet>> entries =
				new HashMap<TASIndex,Production<String,AttribSet>>();
		
		NonTerminal<String,AttribSet> head = 
				new NonTerminal<String,AttribSet>("PARAMLIST");
	
		Terminal<String,AttribSet> inBody = 
				new Terminal<String,AttribSet>("epsilon");
		
		ArrayList<Symbol<String,AttribSet>> body =
				new ArrayList<Symbol<String,AttribSet>>();
		
		body.add(inBody);
		
		Production<String,AttribSet> p = new Production<String,AttribSet>(head, body);
		
		Terminal<String,AttribSet> par = new Terminal<String,AttribSet>(")");
		
		TASIndex tasIndex = new TASIndex(head, par);
		entries.put(tasIndex, p);
		
		head = new NonTerminal<String,AttribSet>("PARAMLIST");
		Token<Integer> parToken = new Token<Integer>(")", 0);
		tasIndex = new TASIndex(head, parToken);
		
		p = entries.get(tasIndex);
		
		assertEquals(false, p == null);		
	}
}