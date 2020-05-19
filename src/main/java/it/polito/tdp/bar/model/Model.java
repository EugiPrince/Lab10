package it.polito.tdp.bar.model;

public class Model {
	
	private Simulator sim;
	
	public Model() {
		sim = new Simulator();
	}

	public void setTavoli(int numTavoli, int posti) {
		this.sim.setTavoli(numTavoli, posti);
	}
	
	public void runSimulator() {
		this.sim.run();
	}
	
	public int getClientiTot() {
		return this.sim.getClientiTot();
	}
	
	public int getClientiSoddisfatti() {
		return this.sim.getClientiSoddisfatti();
	}
	
	public int getClientiInsoddisfatti() {
		return this.sim.getClientiInsoddisfatti();
	}
}
