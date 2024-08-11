package com.beHOHtest.eventos.repository;

import com.beHOHtest.eventos.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface eventoRepository extends JpaRepository<Evento, Integer> {
    Optional<Evento> findByNome(String nome);
}

