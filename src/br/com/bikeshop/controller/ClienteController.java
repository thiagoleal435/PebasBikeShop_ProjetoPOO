package br.com.bikeshop.controller;

import br.com.bikeshop.dao.GerenciadorDados;
import br.com.bikeshop.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class ClienteController {

    private List<Cliente> clientes; // Cache em memória
    private static final String ARQUIVO_CLIENTES = "clientes.dat";

    public ClienteController() {
        // Ao instanciar a Controller, já carregamos os dados do arquivo
        this.clientes = GerenciadorDados.carregar(ARQUIVO_CLIENTES);
        
        // Se o arquivo não existia, a lista vem vazia, mas nunca nula
        if (this.clientes == null) {
            this.clientes = new ArrayList<>();
        }
    }

    // Método chamado pela Tabela (JTable) para mostrar os dados
    public List<Cliente> listarTodos() {
        return this.clientes;
    }

    // Método chamado pelo Botão "Salvar" da tela
    public boolean salvar(String nome, String cpf, String telefone) {
        // 1. Validação (Regra de Negócio)
        if (nome.isEmpty() || cpf.isEmpty()) {
            System.out.println("Erro: Nome e CPF são obrigatórios.");
            return false; // Falha na validação
        }

        // 2. Criação do Objeto
        Cliente novoCliente = new Cliente(nome, cpf, telefone);

        // 3. Adiciona na lista em memória
        this.clientes.add(novoCliente);

        // 4. Manda o DAO gravar no disco
        GerenciadorDados.salvar(this.clientes, ARQUIVO_CLIENTES);
        
        System.out.println("Cliente salvo com sucesso!");
        return true;
    }
    
    // Método para deletar (Bônus para o CRUD)
    public void excluir(int indiceNaTabela) {
        if (indiceNaTabela >= 0 && indiceNaTabela < clientes.size()) {
            this.clientes.remove(indiceNaTabela);
            GerenciadorDados.salvar(this.clientes, ARQUIVO_CLIENTES);
        }
    }
    
    public boolean atualizar(int indice, String nome, String cpf, String telefone) {
        if (indice >= 0 && indice < this.clientes.size()) {
            // Validação simples
            if (nome.isEmpty() || cpf.isEmpty()) {
                return false;
            }

            // Pega o cliente existente e muda os dados (precisa dos Setters no Model!)
            Cliente c = this.clientes.get(indice);
            c.setNome(nome);
            c.setCpf(cpf);
            c.setTelefone(telefone);

            // Salva a lista atualizada no arquivo
            GerenciadorDados.salvar(this.clientes, ARQUIVO_CLIENTES);
            return true;
        }
        return false;
    }
    
    // Método auxiliar para pegar um cliente específico (usaremos para preencher a tela de edição)
    public Cliente getCliente(int indice) {
        if (indice >= 0 && indice < this.clientes.size()) {
            return this.clientes.get(indice);
        }
        return null;
    }
}