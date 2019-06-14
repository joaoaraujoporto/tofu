package io.tofu.teprl.machines.grammar;

import java.util.ArrayList;

import io.tofu.commons.symbol.Symbol;

public class Expander {
	
	public static <T,R> ArrayList<Symbol<T,R>> subword(ArrayList<Symbol<T,R>> word,
			int beginIndex) {
		return subword(word, beginIndex, word.size() - 1);
	}
	
	public static <T,R> ArrayList<Symbol<T,R>> subword(ArrayList<Symbol<T,R>> word,
			int beginIndex, int endIndex) {
		ArrayList<Symbol<T,R>> subword = new ArrayList<Symbol<T,R>>();
		
		int i;
		for (i = 0; i < beginIndex; i++);
		for (; i <= endIndex; i++)
			subword.add(word.get(i));
		
		return subword;
	}
}
