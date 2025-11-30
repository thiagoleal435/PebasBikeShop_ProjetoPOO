package br.com.bikeshop.model;

import java.io.Serializable;

public abstract class Produto implements Serializable {
	private static final long serialVersionUID = 1L; // Boa prática para versão da classe
	
	protected String codigo;
    protected String nome;
    protected double preco;
    protected int estoqueAtual;
    protected int estoqueMinimo; // Cada produto tem o seu alerta
    
    // Contructor
    public Produto(String codigo, String nome, double preco, int estoqueMinimo) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.preco = preco;
		this.estoqueMinimo = estoqueMinimo;
		this.estoqueAtual = 0;
	}


    // Getters e Setters
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(int estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	public int getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}
}
