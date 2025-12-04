package br.com.bikeshop.view;

import br.com.bikeshop.controller.VendaController;
import br.com.bikeshop.model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaHistoricoVendas extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private VendaController controller;

    public TelaHistoricoVendas() {
        super("Histórico de Vendas");
        this.controller = new VendaController();

        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Colunas conforme solicitado
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Fatura (Cód)");
        modeloTabela.addColumn("Data");
        modeloTabela.addColumn("Cliente");
        modeloTabela.addColumn("Produto");
        modeloTabela.addColumn("Qtd");
        modeloTabela.addColumn("Valor Total");

        tabela = new JTable(modeloTabela);
        // Ajustes visuais de largura
        tabela.getColumnModel().getColumn(0).setPreferredWidth(80); // Fatura
        tabela.getColumnModel().getColumn(4).setPreferredWidth(40); // Qtd
        
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // 2. Botões
        JPanel painelBotoes = new JPanel();
        
        JButton btnVerComprovante = new JButton("Ver Comprovante Original");
        btnVerComprovante.setBackground(new Color(173, 216, 230)); // Azul claro
        
        JButton btnExcluir = new JButton("Remover Venda");
        btnExcluir.setBackground(new Color(255, 160, 160)); // Vermelho claro

        painelBotoes.add(btnVerComprovante);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- AÇÕES ---

        // AÇÃO: Ver Comprovante
        btnVerComprovante.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                Venda v = controller.getVenda(linha);
                // Reutilizamos a TelaFatura passando a venda recuperada!
                new TelaFatura(this, v).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma venda na tabela para ver o comprovante.");
            }
        });

        // AÇÃO: Excluir
        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Deseja excluir esta venda?\nIsso apagará o comprovante e devolverá os itens ao estoque.", 
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirVenda(linha);
                    atualizarTabela(); // Recarrega a tela
                    JOptionPane.showMessageDialog(this, "Venda removida com sucesso.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma venda para remover.");
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Venda> lista = controller.listarVendas();

        for (Venda v : lista) {
            modeloTabela.addRow(new Object[]{
                v.getCodigoUnico(),
                v.getData(),
                v.getCliente().getNome(), // Pega só o nome para caber na tabela
                v.getProduto().getDescricao(),
                v.getQuantidade(),
                String.format("R$ %.2f", v.getValorTotal())
            });
        }
    }
}