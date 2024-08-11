package com.beHOHtest.eventos.service;

import com.beHOHtest.eventos.model.Evento;
import com.beHOHtest.eventos.repository.eventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class eventoService {

    @Autowired
    private eventoRepository _eventoRepository;

    public Evento createEvento(Evento eventoModel) {
        if (eventoModel.getNome() == null || eventoModel.getNome().isEmpty() ||eventoModel.getVagas() < 1) {
            throw new RuntimeException("Preencha todos os campos.");
        }

        if (eventoModel.getDataHoraInicio() == null || eventoModel.getDataHoraFim() == null) {
            throw new RuntimeException("Por favor, adicione uma data de início e uma data fim para o evento.");
        }

        if (eventoModel.getDataHoraInicio().isAfter(eventoModel.getDataHoraFim())) {
            throw new RuntimeException("A data e hora de início não podem ser após a data e hora de fim.");
        }

        Optional<Evento> eventoExistente = _eventoRepository.findByNome(eventoModel.getNome());

        if (eventoExistente.isPresent()) {
            throw new RuntimeException("Já existe um evento com este nome, apague o anterior caso queira postá-lo novamente.");
        }

        return _eventoRepository.save(eventoModel);
    }
}
