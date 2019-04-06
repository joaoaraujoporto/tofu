package io.tofu.commons.ts;

public class PositionInCode {
	private int lineNumber;
	private int colNumberBegin;
	
	public PositionInCode(int lineNumber, int colNumberBegin) {
		this.lineNumber = lineNumber;
		this.colNumberBegin = colNumberBegin;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public int getColNumberBegin() {
		return colNumberBegin;
	}

	@Override
	public String toString() {
		return "[" + lineNumber + "," + colNumberBegin+ "]";
	}
}
