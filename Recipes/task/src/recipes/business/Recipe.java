package recipes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    Long id;

    @NotBlank(message = "name is blank")
    @Column
    String name;

    @NotBlank(message = "category is blank")
    @Column
    String category;

    @Column
    String date;

    @NotBlank(message = "description is blank")
    @Column
    String description;

    @Size(min = 1)
    @Column
    @ElementCollection
    @NotNull
    private List<String> ingredients;

    @Size(min = 1)
    @Column
    @ElementCollection
    @NotNull
    private List<String> directions;

    //implement many to one to author(email)
    @JsonIgnore
    @Column
    String ownerEmail;

    public Recipe(String ownerEmail, String name, String category, String date, String description, List<String> ingredients, List<String> directions) {
        this.ownerEmail = ownerEmail;
        this.name = name;
        this.category = category;
        this.date = date;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }
}
