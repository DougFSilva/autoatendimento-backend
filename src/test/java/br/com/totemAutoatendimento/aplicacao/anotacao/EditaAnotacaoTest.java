package br.com.totemAutoatendimento.aplicacao.anotacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;

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

import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosCriarOuEditarAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosDeAnotacao;
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

class EditaAnotacaoTest {
	
	@Mock
	private AnotacaoRepository repository;
	
	@Mock
	private StandardLogger logger;
	
	@Captor
	private ArgumentCaptor<Anotacao> anotacaoCaptor;
	
	private EditaAnotacao editaAnotacao;
	
	@BeforeEach
	public void beforeEach() {
		MockitoAnnotations.openMocks(this);
		this.editaAnotacao = new EditaAnotacao(repository, logger);
	}
	
	@Test
	void deveriaEditarUmaAnotacaoPorUmPerfilAdministrador() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		testarEdicaoDeAnotacao(administrador);
	}
	
	@Test
	void deveriaEditarUmaAnotacaoPorUmPerfilFuncionario() {
		Usuario funcionario = usuario();
		funcionario.getPerfis().add(new Perfil(TipoPerfil.FUNCIONARIO));
		testarEdicaoDeAnotacao(funcionario);
	}
	
	@Test
	void naoDeveriaEditarUmaAnotacaoPorUmPerfilTotem() {
		Usuario totem = usuario();
		totem.getPerfis().add(new Perfil(TipoPerfil.TOTEM));
		DadosCriarOuEditarAnotacao dados = new DadosCriarOuEditarAnotacao(anotacao().getDescricao(), anotacao().getNivelDeImportancia());
		assertThrows(UsuarioSemPermissaoException.class, () -> editaAnotacao.editar(anotacao().getId(), dados, totem));
		Mockito.verifyNoInteractions(repository);
		Mockito.verifyNoInteractions(logger);
	}
	
	@Test
	void naoDeveriaTentarEditarUmaAnotacaoQueNaoFoiEncontradaNoBancoDeDados() {
		Usuario administrador = usuario();
		administrador.getPerfis().add(new Perfil(TipoPerfil.ADMINISTRADOR));
		DadosCriarOuEditarAnotacao dados = new DadosCriarOuEditarAnotacao(anotacao().getDescricao(), anotacao().getNivelDeImportancia());
		Mockito.when(repository.buscarPeloId(Mockito.anyLong())).thenReturn(Optional.empty());
		assertThrows(ObjetoNaoEncontradoException.class,() -> editaAnotacao.editar(anotacao().getId(), dados, administrador));
		Mockito.verify(repository, never()).salvar(Mockito.any());
		Mockito.verifyNoInteractions(logger);
	}
	
	private void testarEdicaoDeAnotacao(Usuario usuarioAutenticado) {
		DadosCriarOuEditarAnotacao dados = new DadosCriarOuEditarAnotacao(anotacao().getDescricao(), anotacao().getNivelDeImportancia());
		Anotacao anotacaoAtualizada = anotacao();
		anotacaoAtualizada.setDescricao("Anotação Atualizada");
		anotacaoAtualizada.setNivelDeImportancia(NivelDeImportancia.ALTA);
		Mockito.when(repository.buscarPeloId(anotacao().getId())).thenReturn(Optional.of(anotacao()));
		Mockito.when(repository.salvar(Mockito.any())).thenReturn(anotacaoAtualizada);
		DadosDeAnotacao dadosDeAnotacao = editaAnotacao.editar(anotacao().getId(), dados, usuarioAutenticado);
		Mockito.verify(repository).salvar(anotacaoCaptor.capture());
		Mockito.verify(logger).info(String.format(Mockito.anyString(), Mockito.any()), usuarioAutenticado);
		assertEquals(dados.descricao(), anotacaoCaptor.getValue().getDescricao());
		assertEquals(dados.nivelDeImportancia(), anotacaoCaptor.getValue().getNivelDeImportancia());
		assertEquals(anotacaoAtualizada.getId(), dadosDeAnotacao.getId());
		assertEquals(anotacaoAtualizada.getTimestamp(), dadosDeAnotacao.getTimestamp());
		assertEquals(anotacaoAtualizada.getRegistrador().getUsername(), dadosDeAnotacao.getRegistrador());
		assertEquals(anotacaoAtualizada.getNivelDeImportancia(), dadosDeAnotacao.getNivelDeImportancia());
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
