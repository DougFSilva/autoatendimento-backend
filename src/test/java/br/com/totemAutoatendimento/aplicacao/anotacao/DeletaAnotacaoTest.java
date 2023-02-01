package br.com.totemAutoatendimento.aplicacao.anotacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.anotacao.NivelDeImportancia;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.UsuarioSemPermissaoException;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

class DeletaAnotacaoTest {

	@Mock
	private AnotacaoRepository repository;
	
	@Captor
	private ArgumentCaptor<Anotacao> anotacaoCaptor;
	
	@Mock
	private StandardLogger logger;
	
	private DeletaAnotacao deletaAnotacao;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.deletaAnotacao = new DeletaAnotacao(repository, logger);
	}
	
	@Test
	void deveriaDeletarUmaAnotacaoPorUmPerfilAdministrador() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		testarDeleteDeAnotacao(administrador);
	}
	
	@Test
	void deveriaDeletarUmaAnotacaoPorUmPerfilFuncionario() {
		Usuario funcionario = usuario();
		funcionario.getPerfis().add(new Perfil(TipoPerfil.FUNCIONARIO));
		testarDeleteDeAnotacao(funcionario);
	}
	
	@Test
	void naoDeveriaDeletarUmaAnotacaoPorUmPerfilTotem() {
		Usuario funcionario = usuario();
		funcionario.getPerfis().add(new Perfil(TipoPerfil.TOTEM));
		assertThrows(UsuarioSemPermissaoException.class, () -> deletaAnotacao.deletar(anotacao().getId(), funcionario));
		verifyNoInteractions(repository);
		verifyNoMoreInteractions(logger);
	}
	
	@Test
	void naoDeveriaTentarDeletarUmaAnotacaoQueNaoFoiEncontradaNoBancoDeDados() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		Mockito.when(repository.buscarPeloId(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(ObjetoNaoEncontradoException.class, () -> deletaAnotacao.deletar(anotacao().getId(), administrador));
		Mockito.verify(repository, never()).deletar(null);
		Mockito.verifyNoInteractions(logger);
	}
	
	private void testarDeleteDeAnotacao(Usuario usuarioAutenticado) {
		Mockito.when(repository.buscarPeloId(anotacao().getId())).thenReturn(Optional.of(anotacao()));
		deletaAnotacao.deletar(anotacao().getId(), usuarioAutenticado);
		Mockito.verify(repository).deletar(anotacaoCaptor.capture());
		Mockito.verify(logger).info(String.format(Mockito.anyString(), Mockito.any()), usuarioAutenticado);
		assertEquals(anotacao(), anotacaoCaptor.getValue());
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
