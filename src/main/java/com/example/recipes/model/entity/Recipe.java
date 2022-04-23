package com.example.recipes.model.entity;

import com.example.recipes.model.interfaces.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonView(View.SearchView.class)
@Entity
public class Recipe extends IdentityIdBaseEntity {
    @NotBlank(message = "Recipe must have a name")
    private String name;

    @NotBlank(message = "Recipe must have a category")
    private String category;

    @LastModifiedDate
    private LocalDateTime date;

    @NotBlank(message = "Recipe must have a description")
    private String description;

    @ElementCollection
    @NotEmpty(message = "Recipe must have at least 1 ingredient")
    private List<String> ingredients;

    @ElementCollection
    @NotEmpty(message = "Recipe must have at least 1 direction")
    private List<String> directions;

    public Recipe(String name, String category, String description, List<String> ingredients, List<String> directions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }
}