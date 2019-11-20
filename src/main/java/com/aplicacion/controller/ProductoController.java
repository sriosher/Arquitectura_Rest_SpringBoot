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
import com.aplicacion.model.Producto;
import com.aplicacion.service.IProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	private IProductoService service;

	@GetMapping
	public ResponseEntity<List<Producto>> listar() {
		List<Producto> lista_productos = service.listar();
		return new ResponseEntity<List<Producto>>(lista_productos, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Producto> leerPorId(@PathVariable("id") Integer id) {
		Producto producto = service.leerPorId(id);

		if (producto.getIdProducto() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}

	/*
	 * @PostMapping public ResponseEntity<Paciente> registrar(@Valid @RequestBody
	 * Paciente paciente) { Paciente pac = service.registrar(paciente); return new
	 * ResponseEntity<Paciente>(pac, HttpStatus.OK); }
	 */
	@PostMapping
	public ResponseEntity<Object> registrar(@Valid @RequestBody Producto producto) {
		Producto obj = service.registrar(producto);
		// pacientes/4
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdProducto())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Producto> modificar(@Valid @RequestBody Producto producto) {
		Producto obj = service.modificar(producto);
		return new ResponseEntity<Producto>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Object> eliminar(@PathVariable("id") Integer id) {
		Producto producto = service.leerPorId(id);
		if (producto.getIdProducto() == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		service.eliminar(id);

		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}