package io.tofu.commons.ts;

public class TSEntryVar extends TSEntry {
	private String id;
	private String type;
	private int offset;
	
	public TSEntryVar(String id, String type, int offset) {
		this.id = id;
		this.type = type;
		this.offset = offset;
	}

	public String getId() {
		return id;
	}

	public String getType() {
		return type;
	}
	
	public int getOffset() {
		return offset;
	}
	
	@Override
	public String toString() {
		return "tokenName: " + id +
				"lexeme: " + type +
				"offset: " + offset;
	}
}
