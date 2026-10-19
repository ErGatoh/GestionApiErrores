package com.trinitarias.grupo4.tema5.exception;

public class ProductoDuplicadoException extends RuntimeException {

    public ProductoDuplicadoException(String codigo) {
        super("Ya existe un producto con el código " + codigo);
    }
}