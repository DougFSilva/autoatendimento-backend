package br.com.totemAutoatendimento.infraestrutura.web;

public class ExceptionPadrao {

	private long timestamp;
	private Integer status;
	private String erro;

	public ExceptionPadrao() {
		super();
	}

	public ExceptionPadrao(long timestamp, Integer status, String erro) {
		this.timestamp = timestamp;
		this.status = status;
		this.erro = erro;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

}
