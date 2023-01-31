package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto.DadosDeSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto.DadosEditarSubcategoria;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class EditaSubcategoria {

	private final SubcategoriaRepository repository;

	private final CategoriaRepository categoriaRepository;
	
	private final StandardLogger logger;

	public EditaSubcategoria(SubcategoriaRepository repository, CategoriaRepository categoriaRepository, StandardLogger logger) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
		this.logger = logger;
	}

    public DadosDeSubcategoria editar(Long id, DadosEditarSubcategoria dados, Usuario usuarioAutenticado) {
    	AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
    	Optional<Subcategoria> subcategoria = repository.buscarPeloId(id);
    	if(subcategoria.isEmpty()) {
    		throw  new ViolacaoDeIntegridadeDeDadosException(String.format("Subcategoria com id %d não encontrada!", id));
    	}
        Optional<Subcategoria> subcategoriaPorNome = repository.buscarPeloNome(dados.nome());
        if (subcategoriaPorNome.isPresent() && subcategoriaPorNome.get().getId() != id) {
        	throw new ViolacaoDeIntegridadeDeDadosException(String.format("Subcategoria com nome %s já cadastrada!", dados.nome()));
        }
        Optional<Categoria> categoria = categoriaRepository.buscarPeloId(dados.categoriaId());
		if(categoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Categoria com id %d não encontrada!", dados.categoriaId()));
		}
        subcategoria.get().setNome(dados.nome());
        subcategoria.get().setCategoria(categoria.get());
        Subcategoria subcategoriaEditada = repository.salvar(subcategoria.get());
        logger.info(String.format("Subcategoria de id %d editada!", subcategoriaEditada.getId()), usuarioAutenticado);
        return new DadosDeSubcategoria(subcategoriaEditada);
    }
}
