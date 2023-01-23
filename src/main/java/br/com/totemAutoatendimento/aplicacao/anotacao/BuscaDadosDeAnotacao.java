package br.com.totemAutoatendimento.aplicacao.anotacao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosDeAnotacao;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaDadosDeAnotacao {

	private final AnotacaoRepository repository;

	public BuscaDadosDeAnotacao(AnotacaoRepository repository) {
		this.repository = repository;
	}

	public DadosDeAnotacao buscarPeloId(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Anotacao> anotacao = repository.buscarPeloId(id);
		if (anotacao.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Anotacao com id %d não encontrada!", id));
		}
		return new DadosDeAnotacao(anotacao.get());
	}

	public Page<DadosDeAnotacao> buscarPorData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarPorData(paginacao, dataInicial, dataFinal).map(DadosDeAnotacao::new);
	}

	public Page<DadosDeAnotacao> buscarTodas(Pageable paginacao, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarTodas(paginacao).map(DadosDeAnotacao::new);
	}

}
