package br.com.bikeshop.model;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;
    private String telefone;

    // Constructor
    public Cliente(String nome, String cpf, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }
    
    // Getters and Setters
    public String toString() {
        return "Cliente: " + nome + " (CPF: " + cpf + ")";
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}