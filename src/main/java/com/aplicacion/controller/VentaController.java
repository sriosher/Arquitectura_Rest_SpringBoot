package com.aplicacion.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aplicacion.dto.VentaDTO;
import com.aplicacion.exception.ModeloNotFoundException;
import com.aplicacion.model.Venta;
import com.aplicacion.service.IVentaService;
//import com.mitocode.dto.ConsultaListaExamenDTO;
//import com.mitocode.model.Consulta;

@Controller
@RequestMapping("/ventas")
public class VentaController {

	@Autowired
	private IVentaService service;

	@GetMapping
	public ResponseEntity<List<Venta>> listar() {
		List<Venta> lista_ventas = service.listar();
		return new ResponseEntity<List<Venta>>(lista_ventas, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Venta> leerPorId(@PathVariable("id") Integer id) {
		Venta venta = service.leerPorId(id);

		if (venta.getIdVenta() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Venta>(venta, HttpStatus.OK);
	}

	@GetMapping(value = "/hateoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VentaDTO>> listaHateoas() {

		List<Venta> ventas = new ArrayList<>();
		List<VentaDTO> ventasDTO = new ArrayList<>();
		ventas = service.listar();

		for (Venta c : ventas) {
			VentaDTO d = new VentaDTO();
			d.setIdVenta(c.getIdVenta());
			d.setPersona(c.getPersona());

			// localhost:8080/ventas/1
			WebMvcLinkBuilder linkTo = linkTo(methodOn(VentaController.class).leerPorId((c.getIdVenta())));
			d.add(linkTo.withSelfRel());
			WebMvcLinkBuilder linkTo1 = linkTo(methodOn(PersonaController.class).leerPorId((c.getPersona().getIdPersona())));
			d.add(linkTo1.withSelfRel());
			ventasDTO.add(d);
		}

		return new ResponseEntity<List<VentaDTO>>(ventasDTO, HttpStatus.OK);
	}

	@Transactional
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Venta venta) {
		Venta obj = service.registrar(venta);
		// consultas/4
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdVenta())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Venta> modificar(@Valid @RequestBody Venta venta) {
		Venta obj = service.modificar(venta);
		return new ResponseEntity<Venta>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		Venta venta = service.leerPorId(id);
		if (venta.getIdVenta() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		service.eliminar(id);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
