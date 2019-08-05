package io.tofu.tepas.procedures;

import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntryVar;

public class AssignValue extends Procedure {

	public AssignValue(Object ... args) {
		super(args);
	}
	
	@Override
	public void apply() {
		TS ts = (TS) args[0];
		Integer addr = (Integer) args[1];
		String type = (String) args[2];
		
		TSEntryVar tsEntry = (TSEntryVar) ts.get(addr);
		
		if (!tsEntry.getType().equals(type))
			throw new TypeException();		
	}

}
