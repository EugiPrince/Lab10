package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	//PARAMETRI DI SIMULAZIONE
	//int nTavoli = 15;
	private Map<Integer, Tavolo> tavoli = new HashMap<>();
	private Duration intervallo;
	
	private final LocalTime oraApertura = LocalTime.of(06, 00);
	private final LocalTime oraChiusura = LocalTime.of(21, 00);
	//STATO DEL MONDO
	int tavoliLib;
	private Map<Integer, Tavolo> tavoliLiberi; // = new HashMap<>();
	
	//VALORI IN USCITA
	private int clientiTot;
	private int clientiSoddisfatti;
	private int clientiInsoddisfatti;
	

	//METODI PER IMPOSTARE I PARAMETRI
	public void setTavoli(int numTavoli, int posti) {
		for(int i=0; i<numTavoli; i++) {
			Tavolo temp = new Tavolo(numTavoli+1, posti);
			this.tavoli.put(temp.getId(), temp);
		}
	}
	
	//OUTPUT
	public int getClientiTot() {
		return clientiTot;
	}

	public int getClientiSoddisfatti() {
		return clientiSoddisfatti;
	}

	public int getClientiInsoddisfatti() {
		return clientiInsoddisfatti;
	}
	
	//SIMULAZIONE
	public void run() {
		//Preparazione iniziale
		this.tavoliLiberi = new HashMap<>(this.tavoli); //All'inizio (apertura) avro' tutti i tavoli liberi
		this.clientiTot = 0;
		this.clientiSoddisfatti = 0;
		this.clientiInsoddisfatti = 0;
		
		this.queue.clear();
		
		int nEventi = 0;
		LocalTime oraArrivoCliente = this.oraApertura; //In realta' dovrebbe essere in minuti solo
		do {
			int nClienti = ((int)(Math.random()*(10-1)))+1;
			
			/*
			int minCasuali = 60 + (int)(Math.random() * ((120 - 60) + 1));
			Duration permanenza = Duration.of(minCasuali, ChronoUnit.MINUTES);
			
			float tolleranza = (float)(0.0 + (Math.random() * ((0.9 - 0.0) +1)));
			*/
			Event e = new Event(oraArrivoCliente, EventType.ARRIVO_GRUPPO_CLIENTI, nClienti, null);
			this.queue.add(e);
			
			int intervalloMin = 10 + (int)(Math.random() * ((10 - 1) + 1));
			oraArrivoCliente = oraArrivoCliente.plus(Duration.of(intervalloMin, ChronoUnit.MINUTES));
			nEventi++;
		} while(nEventi < 2000 || oraArrivoCliente.isBefore(oraChiusura));
		
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
	}
	
	private void processEvent(Event e) {
		//Tavolo assegnato = null;
		switch(e.getType()) {
		case ARRIVO_GRUPPO_CLIENTI:
			//int nClienti = ((int)(Math.random()*(10-1)))+1;
			if(this.tavoliLiberi.size()>0) {
				//FORSE I CLIENTI POSSONO SEDERSI
				int nPersone = e.getNumPersone();
				int dist = 99;
				Tavolo temp = null;
				
				this.clientiTot += e.getNumPersone();
				
				//Calcolo a quale tavolo il gruppo dovrebbe andare
				for(Tavolo t : this.tavoliLiberi.values()) {
					if(Math.abs(t.getPosti() - nPersone) < dist) {
						dist = Math.abs(t.getPosti() - nPersone);
						temp = t;
					}
				}
				
				//Se occupano almeno meta' dei posti ok, il tavolo sara' occupato
				if(nPersone>=(temp.getPosti()/2)) {
					//assegnato = temp;
					this.tavoliLiberi.remove(temp.getId());
					
					this.clientiSoddisfatti += nPersone;
					
					//Genera nuovi eventi
					int minCasuali = 60 + (int)(Math.random() * ((120 - 60) + 1));
					Duration permanenza = Duration.of(minCasuali, ChronoUnit.MINUTES);
					
					Event nuovo = new Event(e.getTime().plus(permanenza), EventType.CLIENTE_LIBERA, nPersone, temp);
					this.queue.add(nuovo);
				}
				else {
					float tolleranza = (float)(0.0 + (Math.random() * ((0.9 - 0.0) +1)));
					
					if(tolleranza>=0.45) {
						//this.clientiSoddisfatti += nPersone;
						int minCasuali = 60 + (int)(Math.random() * ((120 - 60) + 1));
						Duration permanenza = Duration.of(minCasuali, ChronoUnit.MINUTES);
						
						Event banco = new Event(e.getTime().plus(permanenza), EventType.CLIENTE_AL_BANCONE, nPersone, null);
						this.queue.add(banco);
					}
					else {
						this.clientiInsoddisfatti += nPersone;
						//Evento se ne va?
					}
				}
			}
			else {
				//CLIENTI AL BANCONE OPPURE VANNO VIA
				int nPersone = e.getNumPersone();
				this.clientiTot += nPersone;
				float tolleranza = (float)(0.0 + (Math.random() * ((0.9 - 0.0) +1)));
				
				if(tolleranza>=0.45) {
					//this.clientiSoddisfatti += nPersone;
					int minCasuali = 60 + (int)(Math.random() * ((120 - 60) + 1));
					Duration permanenza = Duration.of(minCasuali, ChronoUnit.MINUTES);
					Event banco = new Event(e.getTime().plus(permanenza), EventType.CLIENTE_AL_BANCONE, nPersone, null);
					this.queue.add(banco);
				}
				else {
					this.clientiInsoddisfatti += nPersone;
					//Evento se ne va?
				}
			}
			break;
			
		case CLIENTE_AL_BANCONE:
			int nPersone = e.getNumPersone();
			this.clientiSoddisfatti += nPersone;
			break;
			
		case CLIENTE_LIBERA:
			Tavolo liberato = e.getTavolo();
			this.tavoliLiberi.put(liberato.getId(), liberato);
			break;
		}
	}
	
}
