package com.beHOHtest.eventos.controller;

import com.beHOHtest.eventos.model.Usuario;
import com.beHOHtest.eventos.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/usuarios")
public class usuarioController {

    @Autowired
    private usuarioService _usuarioService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody Usuario usuarioModel) {
        try {
            Usuario novoUsuario = _usuarioService.createUser(usuarioModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
