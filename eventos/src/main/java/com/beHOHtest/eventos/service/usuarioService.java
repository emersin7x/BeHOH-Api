package com.beHOHtest.eventos.service;

import com.beHOHtest.eventos.model.Usuario;
import com.beHOHtest.eventos.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class usuarioService {

    @Autowired
    private usuarioRepository _usuarioRepository;

    public Usuario createUser(Usuario usuarioModel) {
        if (usuarioModel.getNome() == null || usuarioModel.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome não pode ser nulo ou vazio.");
        }

        Optional<Usuario> existingUser = _usuarioRepository.findByNome(usuarioModel.getNome());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Já existe um usuário com este nome.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuarioModel.getNome());
        return _usuarioRepository.save(novoUsuario);
    }
}
