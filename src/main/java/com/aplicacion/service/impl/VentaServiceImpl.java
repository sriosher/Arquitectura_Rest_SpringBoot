package com.aplicacion.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aplicacion.model.Venta;
import com.aplicacion.repo.IVentaRepo;
import com.aplicacion.service.IVentaService;

@Service
public class VentaServiceImpl implements IVentaService {

	@Autowired
	private IVentaRepo repo;

	@Override
	public Venta registrar(Venta venta) {
		venta.getDetalleVenta().forEach(det -> {
			det.setVenta(venta);
		});
		return repo.save(venta);
	}

	@Override
	public Venta modificar(Venta obj) {

		return repo.save(obj);
	}

	@Override
	public List<Venta> listar() {

		return repo.findAll();
	}

	@Override
	public Venta leerPorId(Integer id) {
		Optional<Venta> venta = repo.findById(id);
		return venta.isPresent() ? venta.get() : new Venta();
	}

	@Override
	public boolean eliminar(Integer id) {
		repo.deleteById(id);
		return true;
	}

}
