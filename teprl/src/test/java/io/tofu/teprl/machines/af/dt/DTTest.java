package io.tofu.teprl.machines.af.dt;

import java.util.ArrayList;
import org.junit.Test;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;
import io.tofu.teprl.machines.exceptions.OperarMecanismoException;
import io.tofu.commons.symbol.Alphabet;
import junit.framework.TestCase;

public class DTTest extends TestCase {

	@Test
	public void test() {
		DT dt = new DT("myDT");
		Alphabet outro = new Alphabet();
		
		String w1 = " \t\ns";
		String w2 = " \t\n \t \n";
		String w3 = "aba";
		String w4 = " \t\nab";
		
		outro.remove(" ");
		outro.remove("\t");
		outro.remove("\n");
		
		try {
			dt.createState("19", true, false, false, false);
			dt.createState("20", false, false, false, false);
			dt.createState("21", false, true, false, true);
			
			dt.addTransition("19", " ", "20");
			dt.addTransition("19", "\t", "20");
			dt.addTransition("19", "\n", "20");
			
			dt.addTransition("20", " ", "20");
			dt.addTransition("20", "\t", "20");
			dt.addTransition("20", "\n", "20");
			dt.addTransition("20", outro, "21");
			
			dt.init();
			
			assertEquals(dt.getCurrState().getName(), "19");
			assertEquals(dt.read(w1.charAt(0)).getName(), "20");
			assertEquals(dt.read(w1.charAt(1)).getName(), "20");
			assertEquals(dt.read(w1.charAt(2)).getName(), "20");
			//assertEquals(dt.read(w1.charAt(3)).getName(), "21");
			System.out.println(dt.getCurrState().getName());
			assertEquals(dt.getCurrState().isBackable(), true);
			
		} catch (EditarMecanismoException e) {
			e.printStackTrace();
		} catch (OperarMecanismoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
