package com.aplicacion.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplicacion.model.Producto;

@Repository
public interface IProductoRepo extends JpaRepository<Producto, Integer> {

}
