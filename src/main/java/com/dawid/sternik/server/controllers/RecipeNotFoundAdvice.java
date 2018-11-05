package com.dawid.sternik.server.controllers;

import com.dawid.sternik.server.exception.RecipeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RecipeNotFoundAdvice {


    @ResponseBody
    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String recipeNotFoundHandler(RecipeNotFoundException ex) {
        return ex.getMessage();
    }
}
