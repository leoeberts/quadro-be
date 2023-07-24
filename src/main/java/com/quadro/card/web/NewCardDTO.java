package com.quadro.card.web;

import com.quadro.card.domain.NewCard;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewCardDTO implements NewCard {

    @NotNull(message = "Title cannot be null.")
    @Size(max = 100, message = "Title cannot be longer than 100 characters.")
    private String title;

    @Size(max = 1000, message = "Description cannot be longer than 1000 characters.")
    private String description;
}
