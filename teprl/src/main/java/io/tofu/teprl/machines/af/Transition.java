package io.tofu.teprl.machines.af;

public class Transition<S,A> {
	S statout;
	A action;
	S statin;
	
	public Transition(S statout, A action, S statin) {
		this.statout = statout;
		this.action = action;
		this.statin = statin;
	}

	public S getStatout() {
		return statout;
	}

	public A getAction() {
		return action;
	}

	public S getStatin() {
		return statin;
	}
}
