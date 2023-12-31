package io.tofu.tepc_xpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.junit.Test;

import io.tofu.commons.symbol.Token;

public class CompilerTest {

	@Test
	public void test1() {
		System.out.println(" ------- Teste 1 ------- ");
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		try {
			fileReader = new FileReader("sources/arvore_binaria_de_busca.xpp");
			Compiler c = new Compiler(new File("resources/xpp_grammar.xml"));
			
			c.compile(new BufferedReader(fileReader));
    		System.out.println("The syntax analysis have been successfully finished");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	@Test
	public void test2() {
		System.out.println(" ------- Teste 2 ------- ");
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		try {
			fileReader = new FileReader("sources/util.xpp");
			Compiler c = new Compiler(new File("resources/xpp_grammar.xml"));
			
			c.compile(new BufferedReader(fileReader));
    		System.out.println("The syntax analysis have been successfully finished");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	@Test
	public void test3() {
		System.out.println(" ------- Teste 3 ------- ");
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		try {
			fileReader = new FileReader("sources/stack.xpp");
			Compiler c = new Compiler(new File("resources/xpp_grammar.xml"));
			
			c.compile(new BufferedReader(fileReader));
    		System.out.println("The syntax analysis have been successfully finished");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	@Test
	public void test4() {
		System.out.println(" ------- Teste 4 ------- ");
		
		FileReader fileReader;
		BufferedReader bufferedReader;
		
		try {
			fileReader = new FileReader("sources/list.xpp");
			Compiler c = new Compiler(new File("resources/xpp_grammar.xml"));
			
			c.compile(new BufferedReader(fileReader));
    		System.out.println("The syntax analysis have been successfully finished");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
