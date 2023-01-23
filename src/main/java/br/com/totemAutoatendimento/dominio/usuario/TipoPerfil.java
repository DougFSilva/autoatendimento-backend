package br.com.totemAutoatendimento.dominio.usuario;


public enum TipoPerfil {

	ADMINISTRADOR(1, "ROLE_ADMIN"), FUNCIONARIO(2, "ROLE_FUNCIONARIO"), TOTEM(2, "ROLE_TOTEM");

	private long codigo;
	private String descricao;

	private TipoPerfil(long codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public long getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
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
