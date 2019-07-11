package io.tofu.teprl.machines.grammar;

import java.lang.reflect.Method;

import io.tofu.commons.symbol.Symbol;

public class SemanticRule<T,R> {
	Symbol<T,R> s;
	Attribute attribute;
	GLC<T,R> glc;
	Method getAttribute;
	
	SemanticRule(Symbol<T,R> s, Attribute attribute, Method getAttribute, GLC<T,R> glc) {
		this.s = s;
		this.attribute = attribute;
		this.getAttribute = getAttribute;
		this.glc = glc; // For easy access to symbols of grammar
	}
}
