package com.quadro.card.web;

import com.quadro.card.domain.CardService;
import com.quadro.card.domain.NewCardDTO;
import com.quadro.card.domain.data.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService service;

    public CardController(@Autowired CardService service) {
        this.service = service;
    }

    @GetMapping //TODO replace with 'get all board cards'
    public ResponseEntity<List<Card>> getAll() {
        return new ResponseEntity<>(this.service.getAllCards(), HttpStatus.OK);
    }

    @PostMapping //TODO DTO and validation
    public ResponseEntity<Card> create(@RequestBody NewCardDTO newCard) {
        return new ResponseEntity<>(this.service.createCard(newCard), HttpStatus.OK);
    }
}