package io.tofu.teprl.machines.af.dt;

public class DTOutput {
	private boolean accepted;
	private boolean back;
	
	public DTOutput(boolean accepted, boolean back) {
		this.accepted = accepted;
		this.back = back;
	}
	
	public boolean getAccepted() {
		return accepted;
	}
	
	public boolean getBack() {
		return back;
	}
}
