package com.dxc.soporte.model;

import java.sql.Date;
import java.sql.Timestamp;

public class TicketModel {


	private int id;
	private String title;
	private String resolution;
	private String designated;
	private String state;
	private Timestamp fecha_open;
	private Timestamp fecha_close;
	private int tiempo_medio;
	private int usa_recomendacion;
	




	public int getUsa_recomendacion() {
		return usa_recomendacion;
	}

	public void setUsa_recomendacion(int usa_recomendacion) {
		this.usa_recomendacion = usa_recomendacion;
	}

	public Timestamp getFecha_open() {
		return fecha_open;
	}

	public void setFecha_open(Timestamp fecha_open) {
		this.fecha_open = fecha_open;
	}

	public int getTiempo_medio() {
		return tiempo_medio;
	}

	public void setTiempo_medio(int tiempo_medio) {
		this.tiempo_medio = tiempo_medio;
	}

	public Timestamp getFecha_close() {
		return fecha_close;
	}

	public void setFecha_close(Timestamp fecha_close) {
		this.fecha_close = fecha_close;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getDesignated() {
		return designated;
	}

	public void setDesignated(String designated) {
		this.designated = designated;
	}
	


	public TicketModel(int id, String title, String resolution, String designated, String state, Timestamp fecha_open,
			Timestamp fecha_close, int tiempo_medio, int usa_recomendacion) {
		super();
		this.id = id;
		this.title = title;
		this.resolution = resolution;
		this.designated = designated;
		this.state = state;
		this.fecha_open = fecha_open;
		this.fecha_close = fecha_close;
		this.tiempo_medio = tiempo_medio;
		this.usa_recomendacion = usa_recomendacion;
	}



	public TicketModel() {}
	
	@Override
	public String toString() {
		return "TicketModel [id=" + id + ", title=" + title + ", resolution=" + resolution + ", designated="
				+ designated + ", state=" + state + ", fecha_open=" + fecha_open + ", fecha_close=" + fecha_close + ", tiempo_medio=" + tiempo_medio + ", usa_recomendacion=" + usa_recomendacion + "]";
	}
	
	
}
