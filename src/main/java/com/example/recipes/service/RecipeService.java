package com.example.recipes.service;

import com.example.recipes.model.entity.Recipe;
import com.example.recipes.model.entity.User;
import com.example.recipes.persistence.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.recipes.persistence.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    public Recipe findRecipeById(Long id) {
        return  Optional
                .ofNullable(recipeRepository.findRecipeById(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Recipe save(Recipe recipe, Authentication authentication) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        User user = userRepository.findUserByEmail(authentication.getName());
        user.addRecipe(savedRecipe.getId());
        userRepository.save(user);
        return savedRecipe;
    }

    public void delete(Long id, Authentication authentication) {
        Recipe recipe = findRecipeById(id);
        User user = userRepository.findUserByEmail(authentication.getName());
        if (!user.deleteRecipe(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        userRepository.save(user);
        recipeRepository.delete(recipe);
    }

    public void update(Long id, Recipe updatedRecipe, Authentication authentication) {
        Recipe recipe = Optional
                .ofNullable(recipeRepository.findRecipeById(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        User user = userRepository.findUserByEmail(authentication.getName());
        if (!user.getRecipes().contains(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        
        recipe.setName(updatedRecipe.getName());
        recipe.setCategory(updatedRecipe.getCategory());
        recipe.setDescription(updatedRecipe.getDescription());
        recipe.setIngredients(updatedRecipe.getIngredients());
        recipe.setDirections(updatedRecipe.getDirections());
        recipeRepository.save(recipe);
    }

    public List<Recipe> search(String category, String name) {
        if ((category == null && name == null) || (category != null && name != null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return category != null
                ? findRecipeByCategory(category)
                : findRecipeByName(name);
    }

    private List<Recipe> findRecipeByCategory(String category) {
        return recipeRepository.findRecipesByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    private List<Recipe> findRecipeByName(String name) {
        return recipeRepository.findRecipesByNameContainingIgnoreCaseOrderByDateDesc(name);
    }
}
