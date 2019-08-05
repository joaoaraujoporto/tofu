package io.tofu.tepas.procedures;

import io.tofu.commons.ts.TS;
import io.tofu.commons.ts.TSEntryVar;

public class StoreInTS extends Procedure {

	public StoreInTS(Object ... args) {
		super(args);
	}
	
	@Override
	public void apply() {
		TS ts = (TS) args[0];
		String id = (String) args[1];
		String type = (String) args[2];
		Integer offset = (Integer) args[2];
		
		TSEntryVar tsEntry = new TSEntryVar(id, type, offset);
		ts.put(ts.size(), tsEntry);
		
	}

}
