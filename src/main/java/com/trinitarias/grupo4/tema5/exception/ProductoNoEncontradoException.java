package com.trinitarias.grupo4.tema5.exception;

public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(Long id) {
        super("No existe el producto con ID " + id);
    }
}
