package io.tofu.tepc_xpp;


import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

import io.tofu.commons.symbol.TofuToken;

public class CompilerTest {

	@Test
	public void test() {
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		try {
			fileReader = new FileReader("./sources/arvore_binaria_de_busca.xpp");
			bufferedReader = new BufferedReader(fileReader);
			
			Compiler c = new Compiler();
			
			for (TofuToken<Integer> token : c.getTokens(bufferedReader))
				System.out.println(token.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
