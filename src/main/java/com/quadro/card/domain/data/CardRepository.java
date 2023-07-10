package com.quadro.card.domain.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, String> {

    @Override
    List<Card> findAll();

}
