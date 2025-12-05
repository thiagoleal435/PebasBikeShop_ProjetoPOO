package br.com.bikeshop.view;

import br.com.bikeshop.controller.VendaController;
import br.com.bikeshop.model.Bicicleta;
import br.com.bikeshop.model.Peca;
import br.com.bikeshop.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaSelecaoVenda extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private VendaController controller;

    public TelaSelecaoVenda() {
        super("Nova Venda - Selecione um Produto");
        this.controller = new VendaController();

        setSize(1000, 500); // Aumentei a largura para caber as colunas
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. CONFIGURAÇÃO DAS COLUNAS (Requisito 1)
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Cód");
        modeloTabela.addColumn("Tipo");       // Novo
        modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Preço");
        modeloTabela.addColumn("Estoque");
        modeloTabela.addColumn("Aro");        // Novo
        modeloTabela.addColumn("Material");   // Novo
        modeloTabela.addColumn("Tam. Quadro");// Novo
        modeloTabela.addColumn("Faixa Etária"); // Novo
        modeloTabela.addColumn("Uso");

        tabela = new JTable(modeloTabela);
        
        // Ajuste de largura visual
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(60);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botão Vender
        JPanel panel = new JPanel();
        JButton btnVender = new JButton("Vender Selecionado");
        btnVender.setFont(new Font("Arial", Font.BOLD, 14));
        btnVender.setBackground(new Color(100, 200, 100)); 
        
        panel.add(btnVender);
        add(panel, BorderLayout.SOUTH);

        btnVender.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                Produto p = controller.listarProdutos().get(linha);
                
                // Abre a tela de dados, agora passando a referência dessa tela para poder atualizar depois
                new TelaDadosVenda(this, controller, p).setVisible(true);
                
                atualizarTabela(); // Recarrega para mostrar estoque atualizado se a venda ocorrer
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto para vender.");
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        
        for (Produto p : controller.listarProdutos()) {
            String tipo, aroStr = "-", materialStr = "-", tamStr = "-", faixaStr="-", usoStr="-";

            // Lógica para preencher os detalhes (igual à lista de estoque)
            if (p instanceof Bicicleta) {
                tipo = "Bicicleta";
                Bicicleta b = (Bicicleta) p;
                aroStr = String.valueOf(b.getAro());
                materialStr = b.getMaterial();
                tamStr = b.getTamanhoQuadro();
                faixaStr = b.getFaixaEtaria(); // Novo
                usoStr = b.getFinalidade();
            } else {
                Peca peca = (Peca) p;
                tipo = peca.getCategoria();
                
                if ("Quadro".equals(tipo)) {
                    materialStr = peca.getMaterial();
                    tamStr = peca.getMedida();
                } else if ("Roda".equals(tipo)) {
                    aroStr = peca.getMedida();
                } else {
                    tamStr = peca.getMedida(); 
                }
            }
            
            modeloTabela.addRow(new Object[]{
                    p.getCodigo(), tipo, p.getDescricao(), String.format("R$ %.2f", p.getPreco()),
                    p.getEstoqueAtual(), aroStr, materialStr, tamStr, faixaStr, usoStr
                });
        }
    }
}