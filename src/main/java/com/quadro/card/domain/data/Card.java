package com.quadro.card.domain.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "abstract_generator", sequenceName = "card_seq", allocationSize = 1)
public class Card {
    //TODO test validations
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    @Size(max = 100)
    private String title;
    @Size(max = 1000)
    @Column(columnDefinition = "TEXT")
    private String description;
    @NotNull
    private String owner;

    public Card(String title, String description) {
        this.title = title;
        this.description = Optional.ofNullable(description).orElse("");
        this.owner = "test.user@test.com"; //TODO add owner
    }

}
