package com.aplicacion.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplicacion.model.Producto;
import com.aplicacion.repo.IProductoRepo;
import com.aplicacion.service.IProductoService;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoRepo repo;

	@Override
	public Producto registrar(Producto obj) {
		return repo.save(obj);
	}

	@Override
	public Producto modificar(Producto obj) {
		return repo.save(obj);
	}

	@Override
	public List<Producto> listar() {
		return repo.findAll();
	}

	@Override
	public Producto leerPorId(Integer id) {
		Optional<Producto> producto = repo.findById(id);
		return producto.isPresent() ? producto.get() : new Producto();
	}

	@Override
	public boolean eliminar(Integer id) {
		repo.deleteById(id);
		return true;
	}

}
