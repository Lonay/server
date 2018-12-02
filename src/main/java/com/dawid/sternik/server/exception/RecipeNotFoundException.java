package com.dawid.sternik.server.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {
        super("Could not find recipe " + id);
    }
}
