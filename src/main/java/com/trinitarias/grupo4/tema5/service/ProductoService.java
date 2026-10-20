package com.trinitarias.grupo4.tema5.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trinitarias.grupo4.tema5.dto.ProductoMapper;
import com.trinitarias.grupo4.tema5.dto.ProductoRequestDTO;
import com.trinitarias.grupo4.tema5.dto.ProductoResponseDTO;
import com.trinitarias.grupo4.tema5.exception.ProductoDuplicadoException;
import com.trinitarias.grupo4.tema5.exception.ProductoNoEncontradoException;
import com.trinitarias.grupo4.tema5.model.Producto;
import com.trinitarias.grupo4.tema5.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoResponseDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(ProductoMapper::toResponse)
                .toList();
    }

    public ProductoResponseDTO obtenerPorId(Long id) {
        return ProductoMapper.toResponse(buscarEntidad(id));
    }

    public ProductoResponseDTO crear(ProductoRequestDTO datos) {
        comprobarCodigoDisponible(datos.getCodigo());
        Producto guardado = productoRepository.save(ProductoMapper.toEntity(datos));
        return ProductoMapper.toResponse(guardado);
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO datos) {
        Producto productoActual = buscarEntidad(id);

        // El mismo producto puede conservar su código; otro producto no puede usarlo.
        if (!productoActual.getCodigo().equals(datos.getCodigo())) {
            comprobarCodigoDisponible(datos.getCodigo());
        }

        productoActual.setNombre(datos.getNombre());
        productoActual.setCodigo(datos.getCodigo());
        productoActual.setPrecio(datos.getPrecio());

        return ProductoMapper.toResponse(productoRepository.save(productoActual));
    }

    public void eliminar(Long id) {
        productoRepository.delete(buscarEntidad(id));
    }

    // La entidad solo se usa dentro del servicio; nunca sale hacia el controlador.
    private Producto buscarEntidad(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));
    }

    private void comprobarCodigoDisponible(String codigo) {
        if (productoRepository.existsByCodigo(codigo)) {
            throw new ProductoDuplicadoException(codigo);
        }
    }
}
