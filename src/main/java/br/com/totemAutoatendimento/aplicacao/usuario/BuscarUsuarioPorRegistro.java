package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class BuscarUsuarioPorRegistro {

    private UsuarioRepository repository;

    public BuscarUsuarioPorRegistro(UsuarioRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Usuario executar(String registro) {
        Optional<Usuario> usuario = repository.buscarPorRegistro(registro);
        return usuario.orElseThrow(
                () -> new ObjetoNaoEncontradoException("Usuário com registro " + registro + " não encontrado!"));
    }
}
