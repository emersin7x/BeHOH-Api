package com.beHOHtest.eventos.controller;

import com.beHOHtest.eventos.model.Evento;
import com.beHOHtest.eventos.repository.eventoRepository;
import com.beHOHtest.eventos.service.eventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
public class eventoController {

    @Autowired
    private eventoRepository _eventoRepository;
    @Autowired
    private eventoService _eventoService;

    @PostMapping
    public ResponseEntity createEvento(@RequestBody Evento eventoModel) {
        try {
            Evento novoEvento = _eventoService.createEvento(eventoModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoEvento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getEventoById(@PathVariable Integer id) {
        try {
            Optional<Evento> eventoModel = _eventoRepository.findById(id);
            if (!eventoModel.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O evento não pôde ser encontrado!");
            }

            return ResponseEntity.ok(eventoModel.get());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getAllEventos() {
        try {
            List<Evento> eventos = _eventoRepository.findAll();
            return ResponseEntity.ok(eventos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteEvento(@PathVariable Integer id) {
        try {
            if (_eventoRepository.existsById(id)) {
                _eventoRepository.deleteById(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento não pôde ser deletado, pois não existe.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
