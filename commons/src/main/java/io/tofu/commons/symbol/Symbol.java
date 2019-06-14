package io.tofu.commons.symbol;

public class Symbol<Sign,Meaning> {
	protected Sign sign;
	protected Meaning meaning;
	
	public Symbol(Sign sign) {
		this.sign = sign;
	}
	
	public Symbol(Sign sign, Meaning meaning) {
		this.sign = sign;
		this.meaning = meaning;
	}
	
	public Sign getSign() {
		return sign;
	}
	
	public Meaning getMeaning() {
		return meaning;
	}
	
	public void setMeaning(Meaning meaning) {
		this.meaning = meaning;
	}
	
	@Override
	public String toString() {
		String meaning = new String("null");
		
		if (this.meaning != null)
			meaning = this.meaning.toString();
		
		return "<" + sign.toString() + "," + meaning + ">";
	}
	
	@Override
	public boolean equals(Object o) {
		Symbol<?,?> sym = (Symbol<?,?>) o;
		
		if (sign.equals(sym.getSign())) {
			if (meaning == null && sym.getMeaning() == null)
				return true;
			
			if (meaning != null)
				if (sym.getMeaning() != null)
					if (meaning.equals(sym.getMeaning()))
						return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		if (meaning == null)
			return sign.hashCode();
			
		return sign.hashCode() + meaning.hashCode();
	}
}