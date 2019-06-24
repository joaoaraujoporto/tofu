package io.tofu.tepc_xpp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Test;

import io.tofu.commons.symbol.Token;

public class CompilerTest {

	@Test
	public void test() {
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		try {
			fileReader = new FileReader("./sources/arvore_binaria_de_busca.xpp");
			Compiler c = new Compiler(new File("resources/xpp_grammar.xml"));
			
			c.compile(new BufferedReader(fileReader));
    		System.out.println("The syntax analysis have been successfully finished");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
