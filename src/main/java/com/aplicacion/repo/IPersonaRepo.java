package com.aplicacion.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aplicacion.model.Persona;

@Repository
public interface IPersonaRepo extends JpaRepository<Persona, Integer> {

}
