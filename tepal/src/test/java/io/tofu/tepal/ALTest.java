package io.tofu.tepal;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import io.tofu.commons.symbol.Alphabet;
import io.tofu.commons.symbol.Token;
import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntry;
import io.tofu.teprl.machines.af.dt.DT;
import io.tofu.teprl.machines.exceptions.EditarMecanismoException;

public class ALTest {

	@Test
	public void test() throws IOException {
		DT dt1 = new DT("white-space");
		DT dt2 = new DT("opa");
		Alphabet outro = Alphabet.getDefaultSymbols();
		
		try {
			dt1.addSymbols(Alphabet.getWhiteSpace());
			dt2.addSymbols(Alphabet.getOpa());
						
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
			// dt1.addTransition("20", outro, "21");
			dt1.addTransitionByOther("20", "21");
			
			
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
		
		TS ts = new TS();
		
		AL al = new AL(ts, dts);
		
		FileReader sourceCode = new FileReader("./sources/source_test_1.xpp");
		ArrayList<Token<Integer>> tokens = al.out(new BufferedReader(sourceCode));
		
		System.out.println(tokens.toString());
		
		/*
		for (TSEntry tse : ts.values()) {
			System.out.println(tse.getToken() + "," + tse.getLexeme() + ","
					+ tse.getType() + "," + tse.getPosition());
		}
		*/
		
		System.out.println("finges");
		
		AL al2 = new AL(ts, dts);
		
		sourceCode = new FileReader("./sources/arvore_binaria_de_busca.xpp");
		tokens = al2.out(new BufferedReader(sourceCode));
		
		System.out.println(tokens.toString());
		
		/*
		for (TSEntry tse : ts.values()) {
			System.out.println(tse.getToken() + "," + tse.getLexeme() + ","
					+ tse.getType() + "," + tse.getPosition());
		}
		*/
		
	}
}
