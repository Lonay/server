package com.dawid.sternik.server.controllers;

import com.dawid.sternik.server.entity.Recipe;
import com.dawid.sternik.server.entity.RecipeRepository;
import com.dawid.sternik.server.exception.RecipeNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class RecipeController {


    private final RecipeRepository repository;
    private final RecipeResourceAssembler assembler;

    RecipeController(RecipeRepository repository, RecipeResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // Aggregate root

    @GetMapping("/recipes")
    Resources<Resource<Recipe>> all() {
        List<Resource<Recipe>> recipes = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(recipes,
                linkTo(methodOn(RecipeController.class).all()).withSelfRel());
    }

    @PostMapping("/recipes")
    ResponseEntity<?> newRecipe(@RequestBody Recipe newRecipe) throws URISyntaxException {

        Resource<Recipe> resource = assembler.toResource(repository.save(newRecipe));

        return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    // Single item

    @GetMapping("/recipes/{id}")
    Resource<Recipe> one(@PathVariable Long id) {

        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        return assembler.toResource(recipe);
    }

    @PutMapping("/recipes/{id}")
    ResponseEntity<?> replaceRecipe(@RequestBody Recipe newRecipe, @PathVariable Long id) throws URISyntaxException {


        Recipe updatedRecipe = repository.findById(id)
                .map(recipe -> {
                    recipe.setName(newRecipe.getName());
                    recipe.setType(newRecipe.getType());
                    return repository.save(recipe);
                })
                .orElseGet(() -> {
                    newRecipe.setId(id);
                    return repository.save(newRecipe);
                });
        Resource<Recipe> resource = assembler.toResource(updatedRecipe);

        return ResponseEntity.created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/recipes/{id}")
    ResponseEntity<?> deleteRecipe(@PathVariable Long id) {

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }


}
