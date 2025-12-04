package br.com.bikeshop.view;

import br.com.bikeshop.controller.ClienteController;
import br.com.bikeshop.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListaClientes extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ClienteController controller;

    public TelaListaClientes() {
        super("Gestão de Clientes");
        this.controller = new ClienteController(); 

        setSize(600, 400);
        // Não use EXIT_ON_CLOSE se essa tela for chamada pelo Menu Principal, use DISPOSE_ON_CLOSE
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Tabela
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("CPF");
        modeloTabela.addColumn("Telefone");

        tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        // 2. Botões
        JPanel painelBotoes = new JPanel();
        JButton btnNovo = new JButton("Novo Cliente");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- AÇÕES ---

        // Botão NOVO
        btnNovo.addActionListener(e -> {
            // Passa -1 e null, pois é um novo cliente
            TelaCadastroCliente tela = new TelaCadastroCliente(this, controller, -1, null);
            tela.setVisible(true);
            atualizarTabela(); // Atualiza a lista assim que a janela fechar
        });

        // Botão EDITAR (Correção Principal)
        btnEditar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                // Pega o objeto cliente original
                Cliente clienteOriginal = controller.getCliente(linhaSelecionada);
                
                // Abre a tela passando o índice E o objeto para preencher os campos
                TelaCadastroCliente tela = new TelaCadastroCliente(this, controller, linhaSelecionada, clienteOriginal);
                tela.setVisible(true);
                
                atualizarTabela(); // Atualiza a lista assim que a janela fechar
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.");
            }
        });

        // Botão EXCLUIR
        btnExcluir.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                int resp = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?");
                if (resp == JOptionPane.YES_OPTION) {
                    controller.excluir(linhaSelecionada);
                    atualizarTabela();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.");
            }
        });

        // IMPORTANTE: Carrega os dados assim que a tela abre
        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0); // Limpa tabela
        List<Cliente> clientes = controller.listarTodos(); // Busca do controller
        
        for (Cliente c : clientes) {
            modeloTabela.addRow(new Object[]{
                c.getNome(), 
                c.getCpf(), 
                c.getTelefone()
            });
        }
    }
}