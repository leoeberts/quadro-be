package com.quadro.card.domain;

import com.quadro.card.domain.data.Card;
import com.quadro.card.domain.data.CardRepository;
import com.quadro.card.web.NewCardDTO;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Card updateCard(Integer id, NewCardDTO newCard) {
        //TODO not found exception
        //TODO validate owner

        Card card = this.repository.findById(id).orElseThrow();
        card.setTitle(newCard.getTitle());
        card.setDescription(newCard.getDescription());
        return this.repository.save(card);
    }
}
