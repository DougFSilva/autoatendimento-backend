package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosDeMercadoria;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasDeMaiorFaturamento;
import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasMaisVendidas;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaDadosDeMercadorias {

	private final MercadoriaRepository repository;

	private final SubcategoriaRepository subcategoriaRepository;

	public BuscaDadosDeMercadorias(MercadoriaRepository repository, SubcategoriaRepository subcategoriaRepository) {
		this.repository = repository;
		this.subcategoriaRepository = subcategoriaRepository;
	}

	public DadosDeMercadoria buscarPeloId(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		Optional<Mercadoria> mercadoria = repository.buscarPeloId(id);
		if (mercadoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Mercadoria com id %d não encontrada!", id));
		}
		return new DadosDeMercadoria(mercadoria.get());
	}

	public DadosDeMercadoria buscarPeloCodigo(String codigo, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		Optional<Mercadoria> mercadoria = repository.buscarPeloCodigo(codigo);
		if (mercadoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Mercadoria com código %s não encontrada!", codigo));
		}
		return new DadosDeMercadoria(mercadoria.get());
	}

	public Page<DadosDeMercadoria> buscarEmPromocao(Pageable paginacao, Boolean promocao, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		return repository.buscarEmPromocao(paginacao, promocao).map(DadosDeMercadoria::new);
	}

	public Page<DadosDeMercadoria> buscarPelaSubcategoria(Pageable paginacao, Long idSubcategoria, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		Optional<Subcategoria> subcategoria = subcategoriaRepository.buscarPeloId(idSubcategoria);
		if (subcategoria.isEmpty()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Subcategoria com id %d não encontrada!", idSubcategoria));
		}
		return repository.buscarPelaSubcategoria(paginacao, subcategoria.get()).map(DadosDeMercadoria::new);
	}
	
	public Page<RelatorioMercadoriasMaisVendidas> buscarMercadoriasMaisVendidasPelaData(
			Pageable paginacao, 
			LocalDate dataInicial, 
			LocalDate dataFinal, 
			Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarMaisVendidasPelaData(paginacao, dataInicial, dataFinal);
	}
	
	public Page<RelatorioMercadoriasDeMaiorFaturamento> buscarMercadoriasDeMaiorFaturamentoPelaData(
			Pageable paginacao, 
			LocalDate dataInicial, 
			LocalDate dataFinal, 
			Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarComMaiorFaturamentoPelaData(paginacao, dataInicial, dataFinal);
	}

	public Page<DadosDeMercadoria> buscarTodas(Pageable paginacao, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		return repository.buscarTodas(paginacao).map(DadosDeMercadoria::new);
	}

	
}
