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

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.anotacao.NivelDeImportancia;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.UsuarioSemPermissaoException;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

class RemoveAnotacaoTest {

	@Mock
	private AnotacaoRepository repository;
	
	@Mock
	private SystemLogger systemLogger;
	
	@Captor
	private ArgumentCaptor<Anotacao> anotacaoCaptor;
	
	private RemoveAnotacao removeAnotacao;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.removeAnotacao = new RemoveAnotacao(repository, systemLogger);
	}
	
	@Test
	void deveriaRemoverUmaAnotacaoPorUmPerfilAdministrador() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		testarRemocaoDeAnotacao(administrador);
	}
	
	@Test
	void deveriaRemoverUmaAnotacaoPorUmPerfilFuncionario() {
		Usuario funcionario = usuario();
		funcionario.getPerfis().add(new Perfil(TipoPerfil.FUNCIONARIO));
		testarRemocaoDeAnotacao(funcionario);
	}
	
	@Test
	void naoDeveriaRemoverUmaAnotacaoPorUmPerfilTotem() {
		Usuario funcionario = usuario();
		funcionario.getPerfis().add(new Perfil(TipoPerfil.TOTEM));
		assertThrows(UsuarioSemPermissaoException.class, () -> removeAnotacao.remover(anotacao().getId(), funcionario));
		verifyNoInteractions(repository);
		verifyNoMoreInteractions(systemLogger);
	}
	
	@Test
	void naoDeveriaTentarRemoverUmaAnotacaoQueNaoFoiEncontradaNoBancoDeDados() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		Mockito.when(repository.buscarPeloId(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(ObjetoNaoEncontradoException.class, () -> removeAnotacao.remover(anotacao().getId(), administrador));
		Mockito.verify(repository, never()).remover(Mockito.any());
		Mockito.verifyNoInteractions(systemLogger);
	}
	
	private void testarRemocaoDeAnotacao(Usuario usuarioAutenticado) {
		Mockito.when(repository.buscarPeloId(anotacao().getId())).thenReturn(Optional.of(anotacao()));
		removeAnotacao.remover(anotacao().getId(), usuarioAutenticado);
		Mockito.verify(repository).remover(anotacaoCaptor.capture());
		Mockito.verify(systemLogger).info(Mockito.anyString());
		assertEquals(anotacao(), anotacaoCaptor.getValue());
	}
	
	private Usuario usuario() {
		Email email = new Email("fulano@email.com");
		Password password = new Password("P@ssW0rd");
		return new Usuario(1l, "Fulano da Silva", "00011100011", "123456", email, password, new ArrayList<>());
	}
	
	private Anotacao anotacao() {
		return new Anotacao(1l, LocalDateTime.of(2023, 1, 1, 13, 0, 0), usuario(), "Teste de anotação",
				NivelDeImportancia.MEDIA);
	}

}
