package com.beHOHtest.eventos.repository;

import com.beHOHtest.eventos.model.eventoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface eventoUsuarioRepository extends JpaRepository<eventoUsuario, Integer> {
    List<eventoUsuario> findByUsuario_Nome(String nome);
    List<eventoUsuario> findByEvento_Id(Integer idEvento);
    eventoUsuario findByUsuario_NomeAndEvento_Id(String nome, Integer idEvento);
    long countByEvento_Id(Integer idEvento);
    Optional<eventoUsuario> findByIdAndEvento_Id(Integer idBilhete, Integer idEvento);
}