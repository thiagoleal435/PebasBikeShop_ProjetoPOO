package br.com.bikeshop.app;

import br.com.bikeshop.controller.ClienteController;
import br.com.bikeshop.dao.GerenciadorDados;
import br.com.bikeshop.model.Cliente;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
	        // A tela vai fazer exatamente isso:
	        ClienteController controller = new ClienteController();

	        // Simula usuário clicando em salvar
	        boolean sucesso = controller.salvar("Maria da Silva", "555.555.555-55", "8888-0000");
	        
	        if (sucesso) {
	            System.out.println("A tela mostraria um pop-up de sucesso!");
	        }

	        // Simula a tabela pedindo dados
	        System.out.println("Clientes cadastrados: " + controller.listarTodos().size());
	    }
    }
