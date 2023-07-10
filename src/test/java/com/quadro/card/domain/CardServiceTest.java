package com.quadro.card.domain;

import com.quadro.card.domain.data.Card;
import com.quadro.card.domain.data.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository repository;

    @InjectMocks
    private CardService service;

    @Test
    public void shouldReturnAllCards() {
        Card cardOne = new Card(1, "card 1", "card 1111", "owner");
        Card cardTwo = new Card(2, "card 2", "card 2222", "owner2");
        when(this.repository.findAll()).thenReturn(asList(cardOne, cardTwo));

        List<Card> result = this.service.getAllCards();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result).containsExactlyInAnyOrder(cardOne, cardTwo);
    }

    @Test
    public void shouldCreateNewCard() {
        NewCardDTO newCard = NewCardDTO.builder().title("New").description("card").build();

        this.service.createCard(newCard);

        ArgumentCaptor<Card> cardCapture = ArgumentCaptor.forClass(Card.class); //Sakura?
        verify(this.repository).save(cardCapture.capture());
        Card result = cardCapture.getValue();
        assertThat(result.getId()).isNull();
        assertThat(result.getTitle()).isEqualTo(newCard.getTitle());
        assertThat(result.getDescription()).isEqualTo(newCard.getDescription());
        assertThat(result.getOwner()).isEqualTo("test.user@test.com");
    }
}