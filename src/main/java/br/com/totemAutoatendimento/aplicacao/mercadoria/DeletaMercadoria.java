package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class RemoveMercadoria {
    
    private final MercadoriaRepository repository;
    
    private final SystemLogger logger;

    public RemoveMercadoria(MercadoriaRepository repository, SystemLogger logger){
        this.repository = repository;
        this.logger = logger;
    }

    public void remover(Long id, Usuario usuarioAutenticado){
    	AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
    	Optional<Mercadoria> mercadoria = repository.buscarPeloId(id);
		if(mercadoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Mercadoria com id %d não encontrada!", id));
		}
        repository.remover(mercadoria.get());
        logger.info(
        		String.format("Usuário %s - Mercadoria com código %s removida!", 
        				usuarioAutenticado.getRegistro(), mercadoria.get().getCodigo())
        );
    }
}
