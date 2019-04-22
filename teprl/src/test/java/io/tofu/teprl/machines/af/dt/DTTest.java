package io.tofu.teprl.machines.af.dt;

import org.junit.Test;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;
import io.tofu.teprl.machines.exceptions.OperarMecanismoException;
import io.tofu.commons.symbol.Alphabet;
import junit.framework.TestCase;

public class DTTest extends TestCase {

	@Test
	public void test() {
		DT dt = new DT("myDT");
		Alphabet outro = Alphabet.getDefaultSymbols();
		
		String w1 = " \t\ns";
		
		try {
			dt.addSymbols(outro);
						
			outro.remove(" ");
			outro.remove("\t");
			outro.remove("\n");
			
			dt.createState("19", true, false, false, false);
			dt.createState("20", false, false, false, false);
			dt.createState("21", false, true, false, true);
			
			dt.addTransition("19", " ", "20");
			dt.addTransition("19", "\t", "20");
			dt.addTransition("19", "\n", "20");
			
			dt.addTransition("20", " ", "20");
			dt.addTransition("20", "\t", "20");
			dt.addTransition("20", "\n", "20");
			dt.addTransitionByOther("20", "21");
			
			dt.init();
			
			assertEquals("19", dt.getCurrState().getName());
			assertEquals("20", dt.read(w1.charAt(0)).getName());
			assertEquals("20", dt.read(w1.charAt(1)).getName());
			assertEquals("20", dt.read(w1.charAt(2)).getName());
			assertEquals("21", dt.read(w1.charAt(3)).getName());
			assertEquals(true, dt.getCurrState().isBackable());
		} catch (EditarMecanismoException | OperarMecanismoException e) {
			e.printStackTrace();
		}
	}
}
