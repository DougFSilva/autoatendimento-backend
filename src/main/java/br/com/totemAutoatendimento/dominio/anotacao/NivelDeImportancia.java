package br.com.totemAutoatendimento.dominio.anotacao;

import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import lombok.Getter;

@Getter
public enum NivelDeImportancia {

	BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");
	
	private long codigo;
	
	private String descricao;

	NivelDeImportancia(long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public static TipoPerfil toEnum(String descricao) {
		if (descricao == null) {
			return null;
		}
		for (TipoPerfil x : TipoPerfil.values()) {
			if (descricao.equals(x.getDescricao())) {
				return x;
			}

		}
		throw new IllegalArgumentException("Perfil inválido");
	}
}
