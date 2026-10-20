package com.trinitarias.grupo4.tema5.dto;

import com.trinitarias.grupo4.tema5.model.Producto;

/**
 * Convierte entre la entidad JPA y los DTOs.
 * Es el único sitio donde se hace esa traducción.
 */
public final class ProductoMapper {

    private ProductoMapper() {
    }

    public static Producto toEntity(ProductoRequestDTO dto) {
        return new Producto(dto.getNombre(), dto.getCodigo(), dto.getPrecio());
    }

    public static ProductoResponseDTO toResponse(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCodigo(),
                producto.getPrecio());
    }
}
