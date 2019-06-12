package io.tofu.tepc_xpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

import io.tofu.commons.symbol.TofuToken;
import io.tofu.commons.ts.TS;
import io.tofu.tepal.AL;

public class Compiler {
	TS ts;
	AL al;
	
	public Compiler() {
		ts = new TS();
		al = ALGenerator.getAL(ts);
	}

	public Collection<TofuToken<Integer>> getTokens(BufferedReader bufferedReader) throws IOException {
		return al.out(bufferedReader);
	}  
}