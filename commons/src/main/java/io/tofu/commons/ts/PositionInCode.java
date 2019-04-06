package io.tofu.commons.ts;

public class PositionInCode {
	private int lineNumber;
	private int colNumberBegin;
	
	public PositionInCode(int lineNumber, int colNumberBegin) {
		this.lineNumber = lineNumber;
		this.colNumberBegin = colNumberBegin;
	}
}
