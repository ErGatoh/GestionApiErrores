package com.trinitarias.grupo4.tema5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trinitarias.grupo4.tema5.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsByCodigo(String codigo);
}
