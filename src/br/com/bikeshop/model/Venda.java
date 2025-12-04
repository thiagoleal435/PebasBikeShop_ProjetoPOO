package br.com.bikeshop.model;

import java.io.Serializable;
import java.util.UUID; // Para gerar código único

public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codigoUnico;
    private Cliente cliente;
    private Produto produto;
    private int quantidade;
    private String data;
    private double valorTotal;

    public Venda(Cliente cliente, Produto produto, int quantidade, String data) {
        this.cliente = cliente;
        this.produto = produto;
        this.quantidade = quantidade;
        this.data = data;
        // Gera um código aleatório único para a fatura
        this.codigoUnico = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.valorTotal = produto.getPreco() * quantidade;
    }

    // Getters
    public String getCodigoUnico() { return codigoUnico; }
    public Cliente getCliente() { return cliente; }
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }
    public String getData() { return data; }
    public double getValorTotal() { return valorTotal; }
    
    @Override
    public String toString() {
        return "Venda " + codigoUnico + " - " + cliente.getNome();
    }
}