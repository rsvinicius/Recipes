package com.example.recipes.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    private static final String EMAIL_REGEX = "(?i)[\\w!#$%&'*+/=?`{|}~^-]+" +
            "(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,6}";

    @Id
    @Email(regexp = EMAIL_REGEX, message = "Invalid email")
    private String email;

    @NotBlank(message = "Password shouldn't be blank")
    @Size(min = 8, message = "Password should contain at least 8 characters")
    private String password;

    @ElementCollection
    private Set<Long> recipes;

    public void addRecipe(Long id) {
        this.recipes.add(id);
    }

    public boolean deleteRecipe(Long id) {
        return this.recipes.remove(id);
    }
}
