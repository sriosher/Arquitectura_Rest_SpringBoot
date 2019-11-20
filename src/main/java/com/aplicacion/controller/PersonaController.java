package com.aplicacion.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aplicacion.exception.ModeloNotFoundException;
import com.aplicacion.model.Persona;
import com.aplicacion.service.IPersonaService;

@Controller
@RequestMapping("/personas")
public class PersonaController {

	@Autowired
	private IPersonaService service;

	@GetMapping
	public ResponseEntity<List<Persona>> listar() {
		List<Persona> lista_personas = service.listar();
		return new ResponseEntity<List<Persona>>(lista_personas, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Persona> leerPorId(@PathVariable("id") Integer id) {
		Persona persona = service.leerPorId(id);

		if (persona.getIdPersona() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Persona>(persona, HttpStatus.OK);
	}

	/*
	 * @PostMapping public ResponseEntity<Paciente> registrar(@Valid @RequestBody
	 * Paciente paciente) { Paciente pac = service.registrar(paciente); return new
	 * ResponseEntity<Paciente>(pac, HttpStatus.OK); }
	 */
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Persona persona) {
		Persona obj = service.registrar(persona);
		// pacientes/4
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPersona())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Persona> modificar(@Valid @RequestBody Persona persona) {
		Persona per = service.modificar(persona);
		return new ResponseEntity<Persona>(per, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		Persona persona = service.leerPorId(id);
		if (persona.getIdPersona() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		service.eliminar(id);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}