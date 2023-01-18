package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class RemoveComanda {

	private final ComandaRepository repository;

	private final SystemLogger logger;

	public RemoveComanda(ComandaRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
   
    @Transactional
    public void remover(Long id, Usuario usuarioAutenticado){
    	AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
        BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(repository);
        Comanda comanda = buscaComandaPeloId.buscar(id);
        if(comanda.getAberta()){
            throw new RegrasDeNegocioException("Não é possível remover uma comanda aberta!");
        }
        repository.remover(comanda);
        logger.info(
        		String.format("Usuário %s - Comanda com id %d removida!", usuarioAutenticado.getRegistro(), comanda.getId())
        );
    }
}
