package br.am.rbalves.core;

public class Simulacao {

	private int id;
	private String nome;
	private String cpf;
	private String email;
	private double valor;
	private int parcelas;
	private boolean seguro;

	public Simulacao(int id, String nome, String cpf, String email, double valor, int parcelas, boolean seguro) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.valor = valor;
		this.parcelas = parcelas;
		this.seguro = seguro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getParcelas() {
		return parcelas;
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}

	public boolean isSeguro() {
		return seguro;
	}

	public void setSeguro(boolean seguro) {
		this.seguro = seguro;
	}
}
