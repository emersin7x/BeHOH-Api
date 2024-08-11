package com.beHOHtest.eventos.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Set<eventoUsuario> eventosUsuario;
    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<eventoUsuario> getEventosUsuario() {
        return eventosUsuario;
    }

    public void setEventosUsuario(Set<eventoUsuario> eventosUsuario) {
        this.eventosUsuario = eventosUsuario;
    }
}
