package com.aplicacion.dto;


import org.springframework.hateoas.RepresentationModel;

import com.aplicacion.model.Persona;

public class VentaDTO extends RepresentationModel<VentaDTO> {

	private Integer idVenta;
	private Persona persona;
	
	public Integer getIdVenta() {
		return idVenta;
	}
	public void setIdVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}



}
