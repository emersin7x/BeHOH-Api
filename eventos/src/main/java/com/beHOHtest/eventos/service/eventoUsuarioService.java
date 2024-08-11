package com.beHOHtest.eventos.service;

import com.beHOHtest.eventos.model.Evento;
import com.beHOHtest.eventos.model.eventoUsuario;
import com.beHOHtest.eventos.model.Usuario;
import com.beHOHtest.eventos.repository.eventoRepository;
import com.beHOHtest.eventos.repository.eventoUsuarioRepository;
import com.beHOHtest.eventos.repository.usuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class eventoUsuarioService {

    @Autowired
    private eventoUsuarioRepository _eventoUsuarioRepository;
    @Autowired
    private eventoRepository _eventoRepository;
    @Autowired
    private usuarioRepository _usuarioRepository;

    public eventoUsuario cadastrarEventoUsuario(eventoUsuario bilhete) {
        if (bilhete.getUsuario() == null || bilhete.getEvento() == null) {
            throw new RuntimeException("Você precisa cadastrar um usuário para entrar neste evento.");
        }

        Usuario usuarioSelecionado = _usuarioRepository.findByNome(bilhete.getUsuario().getNome())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        Evento eventoSelecionado = _eventoRepository.findById(bilhete.getEvento().getId())
                .orElseThrow(() -> new RuntimeException("Evento não encontrado."));

        if(usuarioNoEvento(bilhete.getUsuario().getNome(), bilhete.getEvento().getId()) == true) {
            throw new RuntimeException("Você já se inscreveu neste Evento.");
        }


        long inscritos = _eventoUsuarioRepository.countByEvento_Id(eventoSelecionado.getId());

        if (eventoSelecionado.getDataHoraInicio().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Não é possível se inscrever após o evento ter sido iniciado.");
        }

        if (inscritos >= eventoSelecionado.getVagas()) {
            throw new RuntimeException("O limite de vagas para este evento foi atingido.");
        }


        eventoUsuario eventoUsuarioCadastrar = new eventoUsuario();
        eventoUsuarioCadastrar.setEvento(eventoSelecionado);
        eventoUsuarioCadastrar.setUsuario(usuarioSelecionado);
        eventoUsuarioCadastrar.setEntrada(false);
        return _eventoUsuarioRepository.save(eventoUsuarioCadastrar);
    }

    public eventoUsuario realizarEntrada(Integer idEvento, Integer idBilhete) {
        Optional<Evento> eventoEncontrado = _eventoRepository.findById(idEvento);

        if (eventoEncontrado.isEmpty()) {
            throw new RuntimeException("Evento não encontrado.");
        }

        Evento evento = eventoEncontrado.get();

        Optional<eventoUsuario> eventoUsuarioEncontrado = _eventoUsuarioRepository.findByIdAndEvento_Id(idBilhete, idEvento);
        if (eventoUsuarioEncontrado.isEmpty()) {
            throw new RuntimeException("O bilhete não foi encontrado.");
        }

        eventoUsuario eventoUsuarioSelecionado = eventoUsuarioEncontrado.get();
        if (eventoUsuarioSelecionado.getEntrada()) {
            throw new RuntimeException("O bilhete já foi utilizado.");
        }

        LocalDateTime agora = LocalDateTime.now();

        LocalDateTime inicioPermitido = evento.getDataHoraInicio().minusHours(1);
        LocalDateTime fimPermitido = evento.getDataHoraFim();
        if (agora.isBefore(inicioPermitido) || agora.isAfter(fimPermitido)) {
            throw new RuntimeException("A entrada no evento só é permitida entre uma hora antes do início e o término do evento.");
        }

        eventoUsuarioSelecionado.setEntrada(true);
        return _eventoUsuarioRepository.save(eventoUsuarioSelecionado);
    }


    public List<Usuario> getUsuariosByEvento(Integer idEvento) {
        List<eventoUsuario> eventoUsuarios = _eventoUsuarioRepository.findByEvento_Id(idEvento);
        if (eventoUsuarios.isEmpty()) {
            throw new RuntimeException("Nenhum usuário encontrado para o evento.");
        }
        return eventoUsuarios.stream()
                .map(eventoUsuario::getUsuario)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Evento> getEventosByUsuario(String nomeUsuario) {
        List<eventoUsuario> eventoUsuarios = _eventoUsuarioRepository.findByUsuario_Nome(nomeUsuario);
        if (eventoUsuarios.isEmpty()) {
            throw new RuntimeException("Nenhum evento encontrado para este usuário.");
        }
        return eventoUsuarios.stream()
                .map(eventoUsuario::getEvento)
                .distinct()
                .collect(Collectors.toList());
    }

    public void cancelarInscricao(String nomeUsuario, Integer idEvento) {
        if(_usuarioRepository.findByNome(nomeUsuario).isEmpty()) {
            throw new RuntimeException("Usuário não encontrado.");
        };

        eventoUsuario eventoUsuario = _eventoUsuarioRepository.findByUsuario_NomeAndEvento_Id(nomeUsuario, idEvento);

        if (eventoUsuario == null) {
            throw new RuntimeException("Você não estava inscrito neste evento.");
        }

        if(eventoUsuario.getEntrada()) {
            throw new RuntimeException("Você não pode cancelar uma inscrição após entrar no evento.");
        }

        _eventoUsuarioRepository.delete(eventoUsuario);
    }

    public boolean usuarioNoEvento(String nomeUsuario, Integer idEvento) {
        eventoUsuario verificaUsuario = _eventoUsuarioRepository.findByUsuario_NomeAndEvento_Id(nomeUsuario, idEvento);

        return verificaUsuario != null;
    }

}
