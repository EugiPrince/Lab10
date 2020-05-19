package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalTime;

public class Event implements Comparable<Event>{

	public enum EventType {
		ARRIVO_GRUPPO_CLIENTI, CLIENTE_AL_BANCONE, CLIENTE_LIBERA
	}

	private LocalTime time; //Forse basterebbe solo un int perche' vogliamo salvare solo i minuti
	private EventType type;
	private int numPersone;
	private Tavolo tavolo;
	//private Duration permanenza;
	//private float tolleranza;

	/**
	 * @param time
	 * @param type
	 * @param numPersone
	 * @param permanenza
	 * @param tolleranza
	 */
	public Event(LocalTime time, EventType type, int numPersone, Tavolo tavolo/*, Duration permanenza, float tolleranza*/) {
		super();
		this.time = time;
		this.type = type;
		this.numPersone = numPersone;
		this.tavolo = tavolo;
		//this.permanenza = permanenza;
		//this.tolleranza = tolleranza;
	}

	public LocalTime getTime() {
		return time;
	}


	public void setTime(LocalTime time) {
		this.time = time;
	}

	public EventType getType() {
		return type;
	}


	public void setType(EventType type) {
		this.type = type;
	}

	
	public int getNumPersone() {
		return numPersone;
	}


	public void setNumPersone(int numPersone) {
		this.numPersone = numPersone;
	}

	public Tavolo getTavolo() {
		return tavolo;
	}

	public void setTavolo(Tavolo tavolo) {
		this.tavolo = tavolo;
	}

	/*
	public Duration getPermanenza() {
		return permanenza;
	}


	public void setPermanenza(Duration permanenza) {
		this.permanenza = permanenza;
	}


	public float getTolleranza() {
		return tolleranza;
	}


	public void setTolleranza(float tolleranza) {
		this.tolleranza = tolleranza;
	}
	*/
	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.time);
	}
	
}
