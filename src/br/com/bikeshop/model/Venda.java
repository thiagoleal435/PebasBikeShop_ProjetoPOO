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
    private String metodoPagamento;

    public Venda(Cliente cliente, Produto produto, int quantidade, String data, String metodoPagamento) {
        this.cliente = cliente;
        this.produto = produto;
        this.quantidade = quantidade;
        this.data = data;
        this.metodoPagamento = metodoPagamento;
        // Gera um código aleatório único para a fatura
        this.codigoUnico = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
     // CÁLCULO DO PREÇO COM REGRA DE NEGÓCIO (PIX = 10% OFF)
        double bruto = produto.getPreco() * quantidade;
        if ("Pix".equalsIgnoreCase(metodoPagamento)) {
            this.valorTotal = bruto * 0.90; // Aplica 10% de desconto
        } else {
            this.valorTotal = bruto;
        }
    }

    // Getters
    public String getMetodoPagamento() { return metodoPagamento; }
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