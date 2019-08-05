package io.tofu.tepas;

import static org.junit.Assert.*;

import org.junit.Test;

import io.tofu.tepas.procedures.Procedure;

public class ProcedureTest {

	@Test
	public void test() {
		StringBuilder s = new StringBuilder();
		P p = new P(s);
		p.apply();
		s.append("t");
		
		System.out.println(p.getName());
		System.out.println(s.toString());
	}
	
	class P extends Procedure {

		public P(Object ... args) {
			super(args);
		}
		
		@Override
		public void apply() {
			StringBuilder s = (StringBuilder) args[0];
			s.append("show");
		}
		
	}

}
