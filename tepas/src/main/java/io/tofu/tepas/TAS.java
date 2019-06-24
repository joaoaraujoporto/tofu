package io.tofu.tepas;

import java.util.HashMap;
import java.util.Map;

import io.tofu.commons.symbol.Terminal;
import io.tofu.teprl.machines.grammar.GLC;
import io.tofu.teprl.machines.grammar.Production;

public class TAS {
	private Map<TASIndex,Production<String,AttribSet>> entries;
	private GLC<String,AttribSet> glc;
	
	public TAS(GLC<String,AttribSet> glc) {
		this.glc = glc;
		entries = new HashMap<TASIndex,Production<String,AttribSet>>();
		setEntries(glc);
	}

	private void setEntries(GLC<String,AttribSet> glc) {
		glc.updateFirst();
		glc.updateFollow(new Terminal<String,AttribSet>("endOfSentence"));
		
		/*
		 * When the first of alfa contains epsilon, I just put the
		 * production p for the terminals of follow p in M/TAS and
		 * whenever p is selected under these conditions I just
		 * pop p.head from the stack?
		 */
		for (Production<String,AttribSet> p : glc.getProductions())
			for (Terminal<String,AttribSet> a: glc.getFirst(p.getBody())) {
				if (a.equals(glc.getEpsilon())) {
					for (Terminal<String,AttribSet> b : glc.getFollow(p.getHead()))
						entries.put(new TASIndex(p.getHead(), b), p);
					
					continue;
				}

				entries.put(new TASIndex(p.getHead(), a), p);
			}
	}

	public Map<TASIndex,Production<String,AttribSet>> getEntries() {
		return entries;
	}
	
	public GLC<String,AttribSet> getGLC() {
		return glc;
	}
}

