package com.beHOHtest.eventos.controller;

import com.beHOHtest.eventos.model.Evento;
import com.beHOHtest.eventos.model.eventoUsuario;
import com.beHOHtest.eventos.model.Usuario;
import com.beHOHtest.eventos.service.eventoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/evento-usuario")
public class eventoUsuarioController {

    @Autowired
    private eventoUsuarioService _eventoUsuarioService;

    @PostMapping
    public ResponseEntity criarEventoUsuario(@RequestBody eventoUsuario bilhete) {
        try {
            eventoUsuario eventoUsuarioCadastrar = _eventoUsuarioService.cadastrarEventoUsuario(bilhete);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoUsuarioCadastrar.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/entrada/{idEvento}/{idBilhete}")
    public ResponseEntity realizarEntrada(@PathVariable Integer idEvento, @PathVariable Integer idBilhete) {
        try {
            eventoUsuario eventoUsuarioEntrada = _eventoUsuarioService.realizarEntrada(idEvento, idBilhete);
            return ResponseEntity.ok(eventoUsuarioEntrada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/usuarios/{idEvento}")
    public ResponseEntity getUsuariosByEvento(@PathVariable Integer idEvento) {
        try {
            List<Usuario> usuarios = _eventoUsuarioService.getUsuariosByEvento(idEvento);
            return ResponseEntity.ok(usuarios);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/eventos/{nomeUsuario}")
    public ResponseEntity getEventosByUsuario(@PathVariable String nomeUsuario) {
        try {
            List<Evento> eventos = _eventoUsuarioService.getEventosByUsuario(nomeUsuario);
            return ResponseEntity.ok(eventos);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/cancelarInscricao")
    public ResponseEntity<String> cancelarInscricao(@RequestParam String nomeUsuario, @RequestParam Integer idEvento) {
        try {
            _eventoUsuarioService.cancelarInscricao(nomeUsuario, idEvento);
            return ResponseEntity.ok("Inscrição cancelada com sucesso!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}

