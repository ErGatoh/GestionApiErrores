package com.trinitarias.grupo4.tema5.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.trinitarias.grupo4.tema5.model.Producto;
import com.trinitarias.grupo4.tema5.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "No existe el producto con ID " + id));
    }

    public Producto crear(Producto producto) {
        comprobarCodigoDisponible(producto.getCodigo());
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto datosProducto) {
        Producto productoActual = obtenerPorId(id);

        // El mismo producto puede conservar su código; otro producto no puede usarlo.
        if (!productoActual.getCodigo().equals(datosProducto.getCodigo())) {
            comprobarCodigoDisponible(datosProducto.getCodigo());
        }

        productoActual.setNombre(datosProducto.getNombre());
        productoActual.setCodigo(datosProducto.getCodigo());
        productoActual.setPrecio(datosProducto.getPrecio());

        return productoRepository.save(productoActual);
    }

    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }

    private void comprobarCodigoDisponible(String codigo) {
        if (productoRepository.existsByCodigo(codigo)) {
            throw new IllegalStateException(
                    "Ya existe un producto con el código " + codigo);
        }
    }
}
