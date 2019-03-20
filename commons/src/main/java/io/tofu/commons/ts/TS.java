package io.tofu.commons.ts;

import java.util.LinkedList;

public class TS extends LinkedList<TSEntry> {
	public TS() {
		init();
	}
	
	private void init() {		
		add(new TSEntry("while", null));
		add(new TSEntry("do", null));
		add(new TSEntry("break", null));
		add(new TSEntry("if", null));
		add(new TSEntry("then", null));
		add(new TSEntry("else", null));
	}


	public Integer indexOfBasic(String lexeme) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean containsKeyword(String lexeme) {
		for (int i = 0; i < 6; i++)
			if (get(i).getTokenName().equals(lexeme))
				return true;
		
		return false;
	}
}

