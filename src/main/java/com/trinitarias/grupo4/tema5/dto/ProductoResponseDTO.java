package com.trinitarias.grupo4.tema5.dto;

import java.math.BigDecimal;

/**
 * Datos que la API devuelve al cliente.
 * Es lo único que se expone: la entidad JPA no sale nunca de la API.
 */
public class ProductoResponseDTO {

    private final Long id;
    private final String nombre;
    private final String codigo;
    private final BigDecimal precio;

    public ProductoResponseDTO(Long id, String nombre, String codigo, BigDecimal precio) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }
}
