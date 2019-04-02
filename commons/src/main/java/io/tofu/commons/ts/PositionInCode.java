package io.tofu.commons.ts;

public class PositionInCode {
	private int lineNumber;
	private int colNumberBegin;
	
	PositionInCode(int lineNumber, int colNumberBegin) {
		this.lineNumber = lineNumber;
		this.colNumberBegin = colNumberBegin;
	}
}
