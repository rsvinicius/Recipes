package com.example.recipes.persistence;

import com.example.recipes.model.entity.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    Recipe findRecipeById(long id);

    @Query("select u from Recipe u where lower(u.category) like lower(:category) order by u.date desc")
    List<Recipe> findRecipesByCategoryIgnoreCaseOrderByDateDesc(@Param("category") String category);

    @Query("select u from Recipe u where lower(u.name) like lower(concat('%', :name, '%')) order by u.date desc")
    List<Recipe> findRecipesByNameContainingIgnoreCaseOrderByDateDesc(@Param("name") String name);
}