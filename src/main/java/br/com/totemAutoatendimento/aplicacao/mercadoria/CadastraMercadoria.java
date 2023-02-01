package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosCriarMercadoria;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CadastraMercadoria {

	private final MercadoriaRepository repository;

	private final SubcategoriaRepository subcategoriaRepository;

	private final StandardLogger logger;

	public CadastraMercadoria(MercadoriaRepository repository, SubcategoriaRepository subcategoriaRepository,
			StandardLogger logger) {
		this.repository = repository;
		this.subcategoriaRepository = subcategoriaRepository;
		this.logger = logger;
	}

	@Transactional
	public Mercadoria cadastrar(DadosCriarMercadoria dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		if (repository.buscarPeloCodigo(dados.codigo()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Mercadoria com código %s já cadastrada!", dados.codigo()));
		}
		Optional<Subcategoria> subcategoria = subcategoriaRepository.buscarPeloId(dados.subcategoriaId());
		if (subcategoria.isEmpty()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Subcategoria com id %d não encontrada!", dados.subcategoriaId()));
		}
		Mercadoria mercadoria = new Mercadoria(
				null, 
				dados.codigo(), 
				subcategoria.get(),
				dados.descricao(),
				dados.preco(), 
				dados.promocao(), 
				dados.precoPromocional(), 
				true, 
				"Sem imagem");
		Mercadoria mercadoriaCadastrada = repository.salvar(mercadoria);
		logger.info(String.format("Mercadoria com código %s cadastrada!", mercadoriaCadastrada.getCodigo()), usuarioAutenticado);
		return mercadoriaCadastrada;
	}

}
