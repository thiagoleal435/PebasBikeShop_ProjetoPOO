package br.com.bikeshop.controller;

import br.com.bikeshop.dao.GerenciadorDados;
import br.com.bikeshop.model.Bicicleta;
import br.com.bikeshop.model.Peca;
import br.com.bikeshop.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoController {

    private List<Produto> produtos;
    private static final String ARQUIVO_PRODUTOS = "produtos.dat";

    public ProdutoController() {
        this.produtos = GerenciadorDados.carregar(ARQUIVO_PRODUTOS);
        if (this.produtos == null) {
            this.produtos = new ArrayList<>();
        }
    }
    
    public String gerarProximoCodigo() {
        int proximoId = produtos.size() + 1;
        return String.valueOf(proximoId);
    }

    public List<Produto> listarTodos() { return this.produtos; }

    public Produto getProduto(int indice) {
        if (indice >= 0 && indice < produtos.size()) { return produtos.get(indice); }
        return null;
    }

    public void excluir(int indice) {
        if (indice >= 0 && indice < produtos.size()) {
            produtos.remove(indice);
            GerenciadorDados.salvar(produtos, ARQUIVO_PRODUTOS);
        }
    }

    public boolean salvarOuAtualizar(int indice, String tipo, String codigo, String descricao, 
                                     String precoStr, String estoqueStr, String estoqueMinStr, 
                                     String aroStr, String material, String tamanhoQuadro) {
        try {
            double preco = Double.parseDouble(precoStr.replace("R$", "").replace(",", ".").trim());
            int estoque = Integer.parseInt(estoqueStr);
            int estoqueMin = Integer.parseInt(estoqueMinStr);
            
            Produto novoProduto;
            
            if (tipo.equals("Bicicleta")) {
                int aro = 0;
                try { aro = Integer.parseInt(aroStr); } catch (Exception e) {}
                
                // Bicicleta já tinha material, mantém igual
                novoProduto = new Bicicleta(codigo, descricao, preco, estoqueMin, "Padrão", aro, material, tamanhoQuadro);
            } else {
                // É UMA PEÇA (Quadro, Roda, etc)
                String medidaParaSalvar = "";
                String materialParaSalvar = ""; 

                if (tipo.equals("Quadro")) {
                    medidaParaSalvar = tamanhoQuadro;
                    materialParaSalvar = material; // PEGA O MATERIAL DA TELA
                } else if (tipo.equals("Roda")) {
                    medidaParaSalvar = aroStr;
                    materialParaSalvar = ""; // Roda não tem material definido no requisito
                } else {
                    // Outras peças
                    medidaParaSalvar = tamanhoQuadro; 
                }

                // Cria a peça passando o material agora
                novoProduto = new Peca(codigo, descricao, preco, estoqueMin, tipo, medidaParaSalvar, materialParaSalvar);
            }
            
            novoProduto.setEstoqueAtual(estoque);
            
            if (indice == -1) {
                produtos.add(novoProduto);
            } else {
                produtos.set(indice, novoProduto);
            }
            
            GerenciadorDados.salvar(produtos, ARQUIVO_PRODUTOS);
            return true;
            
        } catch (NumberFormatException e) {
            System.out.println("Erro numérico: " + e.getMessage());
            return false;
        }
    }
}