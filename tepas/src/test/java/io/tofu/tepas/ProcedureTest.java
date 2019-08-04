package io.tofu.tepas;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProcedureTest {

	@Test
	public void test() {
		P p = new P();
		
		System.out.println(p.getName());
	}
	
	class P extends Procedure {

		@Override
		public void apply() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
