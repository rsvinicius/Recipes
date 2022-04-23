package com.example.recipes.controller;

import com.example.recipes.model.entity.Recipe;
import com.example.recipes.model.interfaces.View;
import com.example.recipes.service.RecipeService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
@Validated
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @JsonView(View.IdOnly.class)
    @PostMapping("/new")
    public Recipe saveRecipe(@Valid @RequestBody Recipe recipe, Authentication authentication) {
        return recipeService.save(recipe, authentication);
    }

    @JsonView(View.SearchView.class)
    @GetMapping("/{id}")
    public Recipe getRecipe(@Min(1L) @PathVariable Long id) {
        return recipeService.findRecipeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecipe(@Min(1L) @PathVariable Long id, Authentication authentication) {
        recipeService.delete(id, authentication);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRecipe(@Min(1L) @PathVariable Long id,
                             @Valid @RequestBody Recipe recipe,
                             Authentication authentication) {
        recipeService.update(id, recipe, authentication);
    }

    @JsonView(View.SearchView.class)
    @GetMapping("/search")
    public List<Recipe> search(@RequestParam(required = false) String category,
                               @RequestParam(required = false) String name) {
        return recipeService.search(category, name);
    }
}