package br.com.totemAutoatendimento.aplicacao.mercadoria;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
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
        BuscaMercadoriaPeloId buscarMercadoriaPeloId = new BuscaMercadoriaPeloId(repository);
        Mercadoria mercadoria = buscarMercadoriaPeloId.buscar(id);
        repository.remover(mercadoria);
        logger.info(
        		String.format("Usuário %s - Mercadoria com código %s removida!", 
        				usuarioAutenticado.getRegistro(), mercadoria.getCodigo())
        );
    }
}
