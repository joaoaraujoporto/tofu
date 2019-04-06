package io.tofu.tepc_xpp;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import io.tofu.commons.symbol.Token;
import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntry;
import io.tofu.tepal.AL;

public class ALGeneratorTest {

	@Test
	public void test() throws IOException {
		TS ts = new TS();
		
		AL al = ALGenerator.getAL(ts);
		
		FileReader sourceCode = new FileReader("./sources/arvore_binaria_de_busca.xpp");
		ArrayList<Token> tokens = al.out(new BufferedReader(sourceCode));
		
		System.out.println(tokens.toString());
		
		for (TSEntry tse : ts.values()) {
			System.out.println(tse.getToken() + "," + tse.getLexeme() + ","
					+ tse.getType() + "," + tse.getPosition());
		}
	}
}
