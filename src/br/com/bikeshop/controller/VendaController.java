package br.com.bikeshop.controller;

import br.com.bikeshop.dao.GerenciadorDados;
import br.com.bikeshop.model.Cliente;
import br.com.bikeshop.model.Produto;
import br.com.bikeshop.model.Venda;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class VendaController {

    private List<Venda> vendas;
    private List<Produto> produtos;
    private List<Cliente> clientes;
    
    private static final String ARQUIVO_VENDAS = "vendas.dat";
    private static final String ARQUIVO_PRODUTOS = "produtos.dat";
    private static final String ARQUIVO_CLIENTES = "clientes.dat";

    public VendaController() {
        this.vendas = GerenciadorDados.carregar(ARQUIVO_VENDAS);
        this.produtos = GerenciadorDados.carregar(ARQUIVO_PRODUTOS);
        this.clientes = GerenciadorDados.carregar(ARQUIVO_CLIENTES);
        
        if (this.vendas == null) this.vendas = new ArrayList<>();
        if (this.produtos == null) this.produtos = new ArrayList<>();
        if (this.clientes == null) this.clientes = new ArrayList<>();
    }

    public List<Produto> listarProdutos() { return produtos; }
    public List<Cliente> listarClientes() { return clientes; }
    public List<Venda> listarVendas() { return vendas; }
    
    // Recupera uma venda específica pelo índice
    public Venda getVenda(int indice) {
        if (indice >= 0 && indice < vendas.size()) {
            return vendas.get(indice);
        }
        return null;
    }

    public Venda realizarVenda(Cliente cliente, Produto produtoSelecionado, int qtd, String data) {
        // ... (Mantenha o código de realizarVenda igual ao que já fizemos) ...
        // Vou omitir aqui para economizar espaço, mas mantenha a lógica de baixar estoque e salvar
        
        // Copie a lógica do passo anterior aqui dentro
        // ...
        
        Produto produtoEstoque = null;
        for (Produto p : produtos) {
            if (p.getCodigo().equals(produtoSelecionado.getCodigo())) {
                produtoEstoque = p;
                break;
            }
        }
        if (produtoEstoque == null) return null;
        if (produtoEstoque.getEstoqueAtual() < qtd) throw new IllegalArgumentException("Estoque insuficiente!");
        
        produtoEstoque.setEstoqueAtual(produtoEstoque.getEstoqueAtual() - qtd);
        Venda novaVenda = new Venda(cliente, produtoEstoque, qtd, data);
        this.vendas.add(novaVenda);
        
        GerenciadorDados.salvar(this.produtos, ARQUIVO_PRODUTOS);
        GerenciadorDados.salvar(this.vendas, ARQUIVO_VENDAS);
        salvarComprovanteEmArquivo(novaVenda);
        
        return novaVenda;
    }
    
    // MÉTODO NOVO: Excluir Venda (Estorno)
    public void excluirVenda(int indice) {
        if (indice >= 0 && indice < vendas.size()) {
            Venda v = vendas.get(indice);
            
            // 1. Devolver produto ao estoque (Estorno)
            // Precisamos achar o produto na lista atual de produtos para atualizar
            for (Produto p : produtos) {
                if (p.getCodigo().equals(v.getProduto().getCodigo())) {
                    p.setEstoqueAtual(p.getEstoqueAtual() + v.getQuantidade());
                    break;
                }
            }
            
            // 2. Apagar o arquivo .txt do comprovante
            File arquivo = new File("comprovantes/venda_" + v.getCodigoUnico() + ".txt");
            if (arquivo.exists()) {
                arquivo.delete();
                System.out.println("Arquivo de comprovante deletado.");
            }
            
            // 3. Remover da lista e salvar .dats
            vendas.remove(indice);
            
            GerenciadorDados.salvar(this.produtos, ARQUIVO_PRODUTOS);
            GerenciadorDados.salvar(this.vendas, ARQUIVO_VENDAS);
        }
    }

    private void salvarComprovanteEmArquivo(Venda venda) {
        // ... (Mantenha a lógica do passo anterior) ...
        File diretorio = new File("comprovantes");
        if (!diretorio.exists()) diretorio.mkdir();

        try (PrintWriter writer = new PrintWriter(new FileWriter("comprovantes/venda_" + venda.getCodigoUnico() + ".txt"))) {
            writer.println("FATURA: " + venda.getCodigoUnico());
            writer.println("DATA: " + venda.getData());
            writer.println("CLIENTE: " + venda.getCliente().getNome());
            writer.println("PRODUTO: " + venda.getProduto().getDescricao());
            writer.println("QTD: " + venda.getQuantidade());
            writer.println("TOTAL: R$ " + String.format("%.2f", venda.getValorTotal()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}