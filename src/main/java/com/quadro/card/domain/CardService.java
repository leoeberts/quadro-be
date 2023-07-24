package com.quadro.card.domain;

import com.quadro.card.domain.data.Card;
import com.quadro.card.domain.data.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    //TODO error handling
    private final CardRepository repository;

    public CardService(@Autowired CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> getAllCards() {
        return this.repository.findAll();
    }

    public Card createCard(NewCard newCard) {
        return this.repository.save(new Card(newCard.getTitle(), newCard.getDescription()));
    }
}
