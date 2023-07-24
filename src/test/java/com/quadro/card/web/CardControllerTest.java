package com.quadro.card.web;

import com.quadro.card.domain.CardService;
import com.quadro.card.domain.data.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardControllerTest {

    @Mock
    private CardService service;

    @InjectMocks
    private CardController controller;

    @Test
    public void shouldReturnAllCards() {
        Card cardOne = new Card(1, "card 1", "card 1111", "owner");
        Card cardTwo = new Card(1, "card 2", "card 2222", "owner2");
        when(this.service.getAllCards()).thenReturn(asList(cardOne, cardTwo));

        ResponseEntity<List<Card>> result = this.controller.getAll();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().size()).isEqualTo(2);
        assertThat(result.getBody()).containsExactlyInAnyOrder(cardOne, cardTwo);
    }

    @Test
    public void shouldCreateNewCard() {
        NewCardDTO newCard = NewCardDTO.builder().title("New").description("card").build();

        ResponseEntity<Card> result = this.controller.create(newCard);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(this.service, times(1)).createCard(newCard);
    }
}