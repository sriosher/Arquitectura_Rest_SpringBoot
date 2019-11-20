package com.aplicacion.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplicacion.model.Persona;
import com.aplicacion.repo.IPersonaRepo;
import com.aplicacion.service.IPersonaService;


@Service
public class PersonaServiceImpl implements IPersonaService {

	@Autowired
	private IPersonaRepo repo;

	@Override
	public Persona registrar(Persona obj) {
		return repo.save(obj);
	}

	@Override
	public Persona modificar(Persona obj) {
		return repo.save(obj);
	}

	@Override
	public List<Persona> listar() {
		return repo.findAll();
	}

	@Override
	public Persona leerPorId(Integer id) {
		Optional<Persona> obj = repo.findById(id);
		return obj.isPresent() ? obj.get() : new Persona();
	}

	@Override
	public boolean eliminar(Integer id) {
		repo.deleteById(id);
		return true;
	}

}
