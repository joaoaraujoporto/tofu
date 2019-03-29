package io.tofu.commons.symbol;

import java.util.ArrayList;

public class Alphabet extends ArrayList<String> {
	public Alphabet() {
		for (char c = 'A'; c <= 'Z'; c++)
		    add(String.valueOf(c));
		
		for (char c = 'a'; c <= 'z'; c++)
		    add(String.valueOf(c));
		
		for (char c = '0'; c <= '9'; c++)
		    add(String.valueOf(c));
		
		for (char c = ';'; c <= '>'; c++)
		    add(String.valueOf(c));
		
		for (char c = '('; c <= '+'; c++)
		    add(String.valueOf(c));
		
		for (char c : ".&[]{}|!-/".toCharArray())
			add(String.valueOf(c));
		
		add(String.valueOf(' '));
		add(String.valueOf('\t'));
		add(String.valueOf('\n'));
	}
}

