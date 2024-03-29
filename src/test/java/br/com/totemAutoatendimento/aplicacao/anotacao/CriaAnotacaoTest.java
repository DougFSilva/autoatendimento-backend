package br.com.totemAutoatendimento.aplicacao.anotacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosCriarOuEditarAnotacao;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.anotacao.NivelDeImportancia;
import br.com.totemAutoatendimento.dominio.exception.UsuarioSemPermissaoException;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

class CriaAnotacaoTest {

	@Mock
	private AnotacaoRepository repository;
	
	@Mock
	private StandardLogger logger;
	
	@Captor
	private ArgumentCaptor<Anotacao> anotacaoCaptor;
	
	private CriaAnotacao criaAnotacao;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.criaAnotacao = new CriaAnotacao(repository, logger);
		
	}
	
	@Test
	void deveriaCriarUmaAnotacaoPorUmPerfilAdministrador() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		testarCriacaoDeAnotacao(administrador);
	}
	
	@Test
	void deveriaCriarUmaAnotacaoPorUmPerfilFuncionario() {
		Usuario funcionario = usuario();
		funcionario.getPerfis().add(new Perfil(TipoPerfil.FUNCIONARIO));
		testarCriacaoDeAnotacao(funcionario);
	}
	
	@Test
	void naoDeveriaCriarUmaAnotacaoPorUmPerfilTotem() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.TOTEM));
		DadosCriarOuEditarAnotacao dados = new DadosCriarOuEditarAnotacao(anotacao().getDescricao(), anotacao().getNivelDeImportancia());
		assertThrows(UsuarioSemPermissaoException.class, () -> criaAnotacao.criar(dados, administrador));
		Mockito.verifyNoMoreInteractions(logger);
		Mockito.verifyNoMoreInteractions(repository);
	}
	
	private void testarCriacaoDeAnotacao(Usuario usuarioAutenticado) {
		DadosCriarOuEditarAnotacao dados = new DadosCriarOuEditarAnotacao(anotacao().getDescricao(), anotacao().getNivelDeImportancia());
		Mockito.when(repository.salvar(Mockito.any())).thenReturn(anotacao());
		Anotacao anotacao = criaAnotacao.criar(dados, usuarioAutenticado);
		Mockito.verify(repository).salvar(anotacaoCaptor.capture());
		Mockito.verify(logger).info(String.format(Mockito.anyString(), Mockito.any()), usuarioAutenticado);
		assertEquals(dados.descricao(), anotacaoCaptor.getValue().getDescricao());
		assertEquals(dados.nivelDeImportancia(), anotacaoCaptor.getValue().getNivelDeImportancia());
		assertEquals(usuarioAutenticado, anotacaoCaptor.getValue().getRegistrador());
		assertEquals(anotacao().getId(), anotacao.getId());
		assertEquals(anotacao().getTimestamp(), anotacao.getTimestamp());
		assertEquals(anotacao().getRegistrador(), anotacao.getRegistrador());
		assertEquals(anotacao().getDescricao(), anotacao.getDescricao());
		assertEquals(anotacao().getNivelDeImportancia(), anotacao.getNivelDeImportancia());
	}
	
	private Usuario usuario() {
		Password password = new Password("P@ssW0rd");
		return new Usuario(1l, "123456", password, new ArrayList<>());
	}
	
	private Anotacao anotacao() {
		return new Anotacao(1l, LocalDateTime.of(2023, 1, 1, 13, 0, 0), usuario(), "Teste de anotação",
				NivelDeImportancia.MEDIA);
	}
}
