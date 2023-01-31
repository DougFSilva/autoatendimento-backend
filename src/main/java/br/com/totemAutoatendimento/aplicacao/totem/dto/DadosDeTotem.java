package br.com.totemAutoatendimento.aplicacao.totem.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.dominio.totem.Totem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DadosDeTotem {

	private Long id;

	private String identificador;

	private String localDeInstalacao;

	private String username;
	
	private List<String> perfis = new ArrayList<>();
	
	public DadosDeTotem(Totem totem) {
		this.id = totem.getId();
		this.identificador = totem.getIdentificador();
		this.localDeInstalacao = totem.getLocalDeInstalacao();
		this.username = totem.getUsuario().getUsername();
		totem.getUsuario().getPerfis().forEach(perfil -> this.perfis.add(perfil.getTipo().getDescricao()));
	}
}
