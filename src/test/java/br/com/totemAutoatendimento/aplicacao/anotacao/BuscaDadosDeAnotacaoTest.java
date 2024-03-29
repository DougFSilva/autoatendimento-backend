package br.com.totemAutoatendimento.aplicacao.anotacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosDeAnotacao;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.anotacao.NivelDeImportancia;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.UsuarioSemPermissaoException;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

class BuscaDadosDeAnotacaoTest {

	@Mock
	private AnotacaoRepository repository;

	private BuscaDadosDeAnotacao buscaDadosDeAnotacao;

	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.buscaDadosDeAnotacao = new BuscaDadosDeAnotacao(repository);
	}

	@Test
	void deveriaBuscarOsDadosDeUmaAnotacaoPeloIdPorUmPerfilAdministrador() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		testarBuscaDeAnotacaoPeloId(administrador);
	}

	@Test
	void deveriaBuscarOsDadosDeUmaAnotacaoPeloIdPorUmPerfilFuncionario() {
		Usuario funcionario = usuario();
		funcionario.getPerfis().add(new Perfil(TipoPerfil.FUNCIONARIO));
		testarBuscaDeAnotacaoPeloId(funcionario);
	}

	@Test
	void naoDeveriaBuscarOsDadosDeUmaAnotacaoPeloIdPorUmPerfilTotem() {
		Usuario totem = usuario();
		totem.getPerfis().add(new Perfil(TipoPerfil.TOTEM));
		assertThrows(UsuarioSemPermissaoException.class,
				() -> buscaDadosDeAnotacao.buscarPeloId(anotacao().getId(), totem));
		Mockito.verifyNoInteractions(repository);
	}
	
	@Test
	void deveriaLancarUmaExceptionSeAnotacaoNaoForEncontradaNoBancoDeDados() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		Mockito.when(repository.buscarPeloId(anotacao().getId())).thenReturn(Optional.empty());
		assertThrows(ObjetoNaoEncontradoException.class,
				() -> buscaDadosDeAnotacao.buscarPeloId(anotacao().getId(), administrador));
	}

	@Test
	void deveriaBuscarOsDadosDeAnotacoesPelaDataPorUmPerfilAdministrador() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		testarBuscaDeAnotacoesPelaData(administrador);
	}
	
	@Test
	void deveriaBuscarOsDadosAnotacoesPelaDataPorUmPerfilFuncionario() {
		Usuario funcionario = usuario();
		funcionario.getPerfis().add(new Perfil(TipoPerfil.FUNCIONARIO));
		testarBuscaDeAnotacoesPelaData(funcionario);
	}
	
	@Test
	void naoDeveriaBuscarOsDadosDeAnotacoesPelaDataPorUmPerfilTotem() {
		Usuario totem = usuario();
		totem.getPerfis().add(new Perfil(TipoPerfil.TOTEM));
		assertThrows(UsuarioSemPermissaoException.class,
				() -> buscaDadosDeAnotacao.buscarPelaData(Pageable.unpaged(), LocalDate.now(), LocalDate.now(), totem));
		Mockito.verifyNoInteractions(repository);
	}
	
	@Test
	void deveriaBuscarOsDadosDeTodasAnotacaoPorUmPerfilAdministrador() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		testarBuscaDeTodasAnotacoes(administrador);
	}
	
	@Test
	void deveriaBuscarOsDadosDeTodasAnotacaoPorUmPerfilFuncionario() {
		Usuario funcionario = usuario();
		funcionario.getPerfis().add(new Perfil(TipoPerfil.FUNCIONARIO));
		testarBuscaDeTodasAnotacoes(funcionario);
	}
	
	@Test
	void naoDeveriaBuscarOsDadosDeTodasAnotacoesPorUmPerfilTotem() {
		Usuario totem = usuario();
		totem.getPerfis().add(new Perfil(TipoPerfil.TOTEM));
		assertThrows(UsuarioSemPermissaoException.class,
				() -> buscaDadosDeAnotacao.buscarTodas(Pageable.unpaged(), totem));
		Mockito.verifyNoInteractions(repository);
	}
	
	private void testarBuscaDeAnotacaoPeloId(Usuario usuarioAutenticado) {
		Mockito.when(repository.buscarPeloId(anotacao().getId())).thenReturn(Optional.of(anotacao()));
		DadosDeAnotacao dadosDeAnotacao = buscaDadosDeAnotacao.buscarPeloId(anotacao().getId(), usuarioAutenticado);
		Mockito.verify(repository).buscarPeloId(anotacao().getId());
		assertEquals(anotacao().getId(), dadosDeAnotacao.getId());
		assertEquals(anotacao().getTimestamp(), dadosDeAnotacao.getTimestamp());
		assertEquals(anotacao().getRegistrador().getUsername(), dadosDeAnotacao.getRegistrador());
		assertEquals(anotacao().getNivelDeImportancia(), dadosDeAnotacao.getNivelDeImportancia());
	}
	
	private void testarBuscaDeAnotacoesPelaData(Usuario usuarioAutenticado) {
		Pageable paginacao = Pageable.unpaged();
		LocalDate dataInicial = LocalDate.of(2023, 1, 1);
		LocalDate dataFinal = LocalDate.of(2023, 1, 2);
		Mockito.when(repository.buscarPelaData(paginacao, dataInicial, dataFinal)).thenReturn(anotacoes());
		Page<DadosDeAnotacao> dadosDeAnotacoes = buscaDadosDeAnotacao.buscarPelaData(paginacao, dataInicial, dataFinal, usuarioAutenticado);
		Mockito.verify(repository).buscarPelaData(paginacao, dataInicial, dataFinal);
		for (int i = 0; i < dadosDeAnotacoes.getSize(); i++) {
			DadosDeAnotacao dadosDeAnotacao = dadosDeAnotacoes.getContent().get(i);
			assertEquals(anotacoes().getContent().get(i).getId(), dadosDeAnotacao.getId());
			assertEquals(anotacoes().getContent().get(i).getTimestamp(), dadosDeAnotacao.getTimestamp());
			assertEquals(anotacoes().getContent().get(i).getRegistrador().getUsername(), dadosDeAnotacao.getRegistrador());
			assertEquals(anotacoes().getContent().get(i).getNivelDeImportancia(), dadosDeAnotacao.getNivelDeImportancia());
		}
	}
	
	private void testarBuscaDeTodasAnotacoes(Usuario usuarioAutenticado) {
		Pageable paginacao = Pageable.unpaged();
		Mockito.when(repository.buscarTodas(paginacao)).thenReturn(anotacoes());
		Page<DadosDeAnotacao> dadosDeAnotacoes = buscaDadosDeAnotacao.buscarTodas(paginacao, usuarioAutenticado);
		Mockito.verify(repository).buscarTodas(paginacao);
		for (int i = 0; i < dadosDeAnotacoes.getSize(); i++) {
			DadosDeAnotacao dadosDeAnotacao = dadosDeAnotacoes.getContent().get(i);
			assertEquals(anotacoes().getContent().get(i).getId(), dadosDeAnotacao.getId());
			assertEquals(anotacoes().getContent().get(i).getTimestamp(), dadosDeAnotacao.getTimestamp());
			assertEquals(anotacoes().getContent().get(i).getRegistrador().getUsername(), dadosDeAnotacao.getRegistrador());
			assertEquals(anotacoes().getContent().get(i).getNivelDeImportancia(), dadosDeAnotacao.getNivelDeImportancia());
		}
	}
	
	private Usuario usuario() {
		Password password = new Password("P@ssW0rd");
		return new Usuario(1l, "123456", password, new ArrayList<>());
	}

	private Anotacao anotacao() {
		return new Anotacao(1l, LocalDateTime.of(2023, 1, 1, 13, 0, 0), usuario(), "Teste de anotação",
				NivelDeImportancia.MEDIA);
	}

	private Page<Anotacao> anotacoes() {
		List<Anotacao> anotacoes = new ArrayList<>();
		anotacoes.add(new Anotacao(1l, LocalDateTime.of(2023, 1, 1, 13, 0, 0), usuario(), "Teste de anotação 1",
				NivelDeImportancia.MEDIA));
		anotacoes.add(new Anotacao(2l, LocalDateTime.of(2023, 1, 2, 14, 0, 0), usuario(), "Teste de anotação 2",
				NivelDeImportancia.ALTA));
		return new PageImpl<>(anotacoes);
	}

}
