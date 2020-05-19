package it.polito.tdp.bar.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();

		m.setTavoli(2, 10);
		m.setTavoli(4, 8);
		m.setTavoli(4, 6);
		m.setTavoli(5, 4);
		
		m.runSimulator();
		
		int c = m.getClientiTot();
		int cSod = m.getClientiSoddisfatti();
		int cInsod = m.getClientiInsoddisfatti();
		
		System.out.format("Clienti tot: %d, soddisfatti: %d, inssoddisfatti: %d\n", c, cSod, cInsod);
	}

}
