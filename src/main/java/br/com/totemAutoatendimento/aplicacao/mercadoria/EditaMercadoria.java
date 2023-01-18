package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosDeMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosEditarMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscaSubcategoriaPeloId;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class EditaMercadoria {

	private final MercadoriaRepository repository;

	private final SubcategoriaRepository subcategoriaRepository;
	
	private final SystemLogger logger;

	public EditaMercadoria(MercadoriaRepository repository, SubcategoriaRepository subcategoriaRepository, SystemLogger logger) {
		this.repository = repository;
		this.subcategoriaRepository = subcategoriaRepository;
		this.logger = logger;
	}

	@Transactional
	public DadosDeMercadoria editar(Long id, DadosEditarMercadoria dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Mercadoria> mercadoriaPorCodigo = repository.buscarPeloCodigo(dados.codigo());
		if (mercadoriaPorCodigo.isPresent() && mercadoriaPorCodigo.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Mercadoria com código %s já cadastrada!", dados.codigo()));
		}
		BuscaMercadoriaPeloId buscarMercadoriaPeloId = new BuscaMercadoriaPeloId(repository);
		BuscaSubcategoriaPeloId buscaSubcategoriaPeloId = new BuscaSubcategoriaPeloId(subcategoriaRepository);
		Mercadoria mercadoria = buscarMercadoriaPeloId.buscar(id);
		Subcategoria subcategoria = buscaSubcategoriaPeloId.buscar(dados.subcategoriaId());
		mercadoria.setCodigo(dados.codigo());
		mercadoria.setSubcategoria(subcategoria);
		mercadoria.setDescricao(dados.descricao());
		mercadoria.setPreco(dados.preco());
		mercadoria.setPromocao(dados.promocao());
		mercadoria.setPrecoPromocional(dados.precoPromocional());
		mercadoria.setDisponivel(dados.disponivel());
		Mercadoria mercadoriaEditada = repository.editar(mercadoria);
		logger.info(
				String.format("Usuário %s - Mercadoria com código %s editada!", 
						usuarioAutenticado.getRegistro(), mercadoriaEditada.getCodigo())
		);
		return new DadosDeMercadoria(mercadoriaEditada);
	}
}
