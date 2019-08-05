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
    		Compiler c = new Compiler(new File("resources/stat_sdt.xml"));
    		fileReader = new FileReader(args[0]);
    		bufferedReader = new BufferedReader(fileReader);
    		
    		System.out.println(c.compile(bufferedReader));
    	} catch (Exception e) {
    		System.err.println("Error: " + e.getMessage());
    	} finally {
    		System.exit(0);
    	}
    }
}