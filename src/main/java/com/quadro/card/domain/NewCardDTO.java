package com.quadro.card.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewCardDTO {
    private String title;
    private String description;
}
