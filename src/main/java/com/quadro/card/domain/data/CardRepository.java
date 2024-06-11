package com.quadro.card.domain.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardRepository extends CrudRepository<Card, Integer> {

    @Override
    List<Card> findAll();

}
