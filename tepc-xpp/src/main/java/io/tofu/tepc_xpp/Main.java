package io.tofu.tepc_xpp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import io.tofu.commons.symbol.Token;

public class Main {
	public static void main(String[] args) {
    	if (args.length < 1) {
    		System.err.println("Error: no input file");
    		System.exit(0);
    	}
    		
    	FileReader fileReader;
    	BufferedReader bufferedReader;
    	
    	try {
    		///tepc-xpp/resources/xpp_grammar.xml
    		File grammarFile = new File("resources/xpp_grammar.xml");
    		Compiler c = new Compiler(new File("resources/xpp_grammar.xml"));
    		fileReader = new FileReader(args[0]);
    		bufferedReader = new BufferedReader(fileReader);
    		
    		c.compile(bufferedReader);
    		System.out.println("The syntax analysis have been successfully finished");
    	} catch (Exception e) {
    		System.err.println("Error: " + e.getMessage());
    	} finally {
    		System.exit(0);
    	}
    }
}