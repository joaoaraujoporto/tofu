package io.tofu.tepal;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import io.tofu.commons.symbol.Alphabet;
import io.tofu.commons.ts.TS;
import io.tofu.teprl.machines.af.dt.DT;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;
import io.tofu.teprl.machines.exceptions.OperarMecanismoException;

public class ALTest {

	@Test
	public void test() {
		DT dt1 = new DT("white-space");
		DT dt2 = new DT("opa");
		Alphabet outro = new Alphabet();
		
		try {
			dt1.addSymbols(outro);
			dt2.addSymbols(outro);
						
			outro.remove(" ");
			outro.remove("\t");
			outro.remove("\n");
			
			dt1.createState("19", true, false, false, false);
			dt1.createState("20", false, false, false, false);
			dt1.createState("21", false, true, false, true);
			
			dt1.addTransition("19", " ", "20");
			dt1.addTransition("19", "\t", "20");
			dt1.addTransition("19", "\n", "20");
			
			dt1.addTransition("20", " ", "20");
			dt1.addTransition("20", "\t", "20");
			dt1.addTransition("20", "\n", "20");
			dt1.addTransition("20", outro, "21");
			
			dt2.createState("17", true, false, false, false);
			dt2.createState("18", false, true, false, false);
			
			dt2.addTransition("17", "+", "18");
			dt2.addTransition("17", "-", "18");
			dt2.addTransition("17", "*", "18");
			dt2.addTransition("17", "/", "18");
			dt2.addTransition("17", "%", "18");
		} catch (EditarMecanismoException e) {
			e.printStackTrace();
		}
		
		ArrayList<DT> dts = new ArrayList<DT>();
		dts.add(dt1);
		dts.add(dt2);
		
		AL al = new AL(new TS(), new Alphabet(), dts);
	}
}
