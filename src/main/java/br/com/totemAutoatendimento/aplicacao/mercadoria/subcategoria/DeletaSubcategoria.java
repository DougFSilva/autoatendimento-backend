package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class RemoveSubcategoria {

    private final SubcategoriaRepository repository;

    private final MercadoriaRepository mercadoriaRepository;
    
    private final SystemLogger logger;

    public RemoveSubcategoria(SubcategoriaRepository repository, MercadoriaRepository mercadoriaRepository, SystemLogger logger) {
        this.repository = repository;
        this.mercadoriaRepository = mercadoriaRepository;
        this.logger = logger;
    }
    
    public void remover(Long id, Usuario usuarioAutenticado) {
    	AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
    	Optional<Subcategoria> subcategoria = repository.buscarPeloId(id);
    	if(subcategoria.isEmpty()) {
    		throw  new ViolacaoDeIntegridadeDeDadosException(String.format("Subcategoria com id %d não encontrada!", id));
    	}
        int quantidadeDeMercadoria = mercadoriaRepository.buscarPelaSubcategoria(Pageable.unpaged(), subcategoria.get())
                .getContent().size();
        if (quantidadeDeMercadoria > 0) {
            throw new ViolacaoDeIntegridadeDeDadosException(
                    "Impossível remover subcategoria pois existem mercadorias pertencentes a ela!");
        }
        repository.remover(subcategoria.get());
        logger.info(
        		String.format("Usuário %s - Subcategoria %s removida!", usuarioAutenticado.getRegistro(), subcategoria.get().getNome())
        );
    }
}
