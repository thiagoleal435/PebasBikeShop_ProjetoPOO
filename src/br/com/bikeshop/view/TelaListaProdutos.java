package br.com.bikeshop.view;

import br.com.bikeshop.controller.ProdutoController;
import br.com.bikeshop.model.Bicicleta;
import br.com.bikeshop.model.Peca;
import br.com.bikeshop.model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListaProdutos extends JFrame {

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ProdutoController controller;

    public TelaListaProdutos() {
        super("Gestão de Estoque");
        this.controller = new ProdutoController();

        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Colunas
        modeloTabela = new DefaultTableModel();
        modeloTabela.addColumn("Cód");
        modeloTabela.addColumn("Tipo");
        modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Preço");
        modeloTabela.addColumn("Estoque");
        modeloTabela.addColumn("Aro");
        modeloTabela.addColumn("Material");
        modeloTabela.addColumn("Tam. Quadro");

        tabela = new JTable(modeloTabela);
        
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(60);

        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();
        JButton btnNovo = new JButton("Novo Produto");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnNovo);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações
        btnNovo.addActionListener(e -> {
            new TelaCadastroProduto(this, controller, -1, null).setVisible(true);
            atualizarTabela();
        });

        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0) {
                Produto p = controller.getProduto(linha);
                new TelaCadastroProduto(this, controller, linha, p).setVisible(true);
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um produto.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha >= 0 && JOptionPane.showConfirmDialog(this, "Excluir?") == JOptionPane.YES_OPTION) {
                controller.excluir(linha);
                atualizarTabela();
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Produto> lista = controller.listarTodos();
        
        for (Produto p : lista) {
            String tipo, aroStr = "-", materialStr = "-", tamStr = "-";

            if (p instanceof Bicicleta) {
                tipo = "Bicicleta";
                Bicicleta b = (Bicicleta) p;
                aroStr = String.valueOf(b.getAro());
                materialStr = b.getMaterial();
                tamStr = b.getTamanhoQuadro();
            } else {
                // LÓGICA DE EXIBIÇÃO PARA PEÇAS
                Peca peca = (Peca) p;
                tipo = peca.getCategoria();

                if ("Quadro".equals(tipo)) {
                    materialStr = peca.getMaterial(); // Agora mostra o material do quadro
                    tamStr = peca.getMedida();
                } 
                else if ("Roda".equals(tipo)) {
                    aroStr = peca.getMedida(); // Se é roda, mostra no Aro
                    tamStr = "-"; 
                } 
                else {
                    // Outras peças ficam sem medida específica visível ou usam tamStr se desejar
                    // tamStr = peca.getMedida(); 
                }
            }
            
            modeloTabela.addRow(new Object[]{
                p.getCodigo(),
                tipo,
                p.getDescricao(),
                String.format("R$ %.2f", p.getPreco()),
                p.getEstoqueAtual(),
                aroStr,
                materialStr,
                tamStr
            });
        }
    }
}