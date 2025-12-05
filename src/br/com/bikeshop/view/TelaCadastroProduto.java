package br.com.bikeshop.view;

import br.com.bikeshop.controller.ProdutoController;
import br.com.bikeshop.model.Bicicleta;
import br.com.bikeshop.model.Peca;
import br.com.bikeshop.model.Produto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TelaCadastroProduto extends JDialog {

    private JComboBox<String> cbTipo;
    private JTextField txtCodigo, txtDescricao, txtPreco, txtEstoque, txtEstoqueMin;
    private JTextField txtAro, txtTamanhoQuadro;
    private JComboBox<String> cbMaterial; 
    
    // NOVOS COMPONENTES
    private JComboBox<String> cbFaixaEtaria;
    private JComboBox<String> cbFinalidade;

    private ProdutoController controller;
    private int indiceEdicao;

    public TelaCadastroProduto(Frame parent, ProdutoController controller, int indice, Produto produto) {
        super(parent, true);
        this.controller = controller;
        this.indiceEdicao = indice;
        
        setTitle(produto == null ? "Novo Produto" : "Editar Produto");
        setSize(550, 650); // Aumentei para caber os campos
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        JPanel painelForm = new JPanel();
        painelForm.setLayout(new GridLayout(12, 2, 10, 10)); // Aumentei linhas para 12
        painelForm.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 1. Tipo
        painelForm.add(new JLabel("Tipo de Produto:"));
        String[] tipos = {"Bicicleta", "Quadro", "Roda", "Freio", "Kit Relação", "Selim", "Guidão"};
        cbTipo = new JComboBox<>(tipos);
        painelForm.add(cbTipo);

        // 2. Código
        painelForm.add(new JLabel("Código (Auto):"));
        txtCodigo = new JTextField();
        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new Color(230, 230, 230));
        painelForm.add(txtCodigo);

        // 3. Descrição
        painelForm.add(new JLabel("Descrição/Nome:"));
        txtDescricao = new JTextField(); painelForm.add(txtDescricao);

        // 4. Preço
        painelForm.add(new JLabel("Preço (R$):"));
        txtPreco = new JTextField(); painelForm.add(txtPreco);

        // 5. Estoque
        painelForm.add(new JLabel("Estoque Atual:"));
        txtEstoque = new JTextField("0"); painelForm.add(txtEstoque);

        // 6. Estoque Mínimo
        painelForm.add(new JLabel("Estoque Mínimo:"));
        txtEstoqueMin = new JTextField("5");
        txtEstoqueMin.setEditable(false);
        txtEstoqueMin.setBackground(new Color(245, 245, 220));
        painelForm.add(txtEstoqueMin);

        // 7. Aro
        painelForm.add(new JLabel("Aro (Bikes/Rodas):"));
        txtAro = new JTextField(); painelForm.add(txtAro);

        // 8. Material
        painelForm.add(new JLabel("Material (Bikes/Quadros):"));
        String[] materiais = {"Alumínio", "Ferro", "Carbono"};
        cbMaterial = new JComboBox<>(materiais);
        painelForm.add(cbMaterial);

        // 9. Tamanho
        painelForm.add(new JLabel("Tamanho (Quadro/Bike):"));
        txtTamanhoQuadro = new JTextField(); painelForm.add(txtTamanhoQuadro);

        // 10. Faixa Etária (NOVO)
        painelForm.add(new JLabel("Faixa Etária (Bikes):"));
        String[] faixas = {"Adulto", "Infantil"};
        cbFaixaEtaria = new JComboBox<>(faixas);
        painelForm.add(cbFaixaEtaria);

        // 11. Finalidade (NOVO)
        painelForm.add(new JLabel("Finalidade (Bikes):"));
        String[] fins = {"Urbano", "Rural/MTB", "Estrada"};
        cbFinalidade = new JComboBox<>(fins);
        painelForm.add(cbFinalidade);

        add(painelForm, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        painelBotoes.add(btnCancelar);
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(100, 30));
        btnSalvar.addActionListener(e -> salvar());
        painelBotoes.add(btnSalvar);
        add(painelBotoes, BorderLayout.SOUTH);

        // Listeners
        cbTipo.addActionListener(e -> atualizarCamposVisuais());

        if (produto != null) preencherCampos(produto);
        else txtCodigo.setText(controller.gerarProximoCodigo());
        
        atualizarCamposVisuais();
    }

    private void atualizarCamposVisuais() {
        String tipo = (String) cbTipo.getSelectedItem();
        boolean isBike = "Bicicleta".equals(tipo);
        boolean isQuadro = "Quadro".equals(tipo);
        boolean isRoda = "Roda".equals(tipo);

        txtAro.setEnabled(isBike || isRoda);
        if (!txtAro.isEnabled()) txtAro.setText("");

        cbMaterial.setEnabled(isBike || isQuadro);
        
        txtTamanhoQuadro.setEnabled(isBike || isQuadro);
        if (isRoda) txtTamanhoQuadro.setEnabled(false);
        if (!txtTamanhoQuadro.isEnabled()) txtTamanhoQuadro.setText("");

        // NOVOS CAMPOS: Só habilitam para Bike
        cbFaixaEtaria.setEnabled(isBike);
        cbFinalidade.setEnabled(isBike);
    }

    private void preencherCampos(Produto p) {
        txtCodigo.setText(p.getCodigo());
        txtDescricao.setText(p.getDescricao());
        txtPreco.setText(String.valueOf(p.getPreco()));
        txtEstoque.setText(String.valueOf(p.getEstoqueAtual()));
        txtEstoqueMin.setText(String.valueOf(p.getEstoqueMinimo()));

        if (p instanceof Bicicleta) {
            cbTipo.setSelectedItem("Bicicleta");
            Bicicleta b = (Bicicleta) p;
            txtAro.setText(String.valueOf(b.getAro()));
            cbMaterial.setSelectedItem(b.getMaterial());
            txtTamanhoQuadro.setText(b.getTamanhoQuadro());
            // Preenche novos campos
            cbFaixaEtaria.setSelectedItem(b.getFaixaEtaria());
            cbFinalidade.setSelectedItem(b.getFinalidade());
            
        } else if (p instanceof Peca) {
            Peca peca = (Peca) p;
            cbTipo.setSelectedItem(peca.getCategoria());
            if ("Quadro".equals(peca.getCategoria())) {
                txtTamanhoQuadro.setText(peca.getMedida());
                if(peca.getMaterial() != null) cbMaterial.setSelectedItem(peca.getMaterial());
            } else if ("Roda".equals(peca.getCategoria())) {
                txtAro.setText(peca.getMedida());
            } else {
                txtTamanhoQuadro.setText(peca.getMedida());
            }
        }
    }

    private void salvar() {
        try {
            int estoque = Integer.parseInt(txtEstoque.getText());
            int minimo = Integer.parseInt(txtEstoqueMin.getText());
            if (estoque <= minimo) {
                JOptionPane.showMessageDialog(this, "Estoque deve ser maior que o mínimo ("+minimo+").", "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro nos números."); return;
        }

        boolean sucesso = controller.salvarOuAtualizar(
            indiceEdicao, (String) cbTipo.getSelectedItem(), txtCodigo.getText(), txtDescricao.getText(),
            txtPreco.getText(), txtEstoque.getText(), txtEstoqueMin.getText(), txtAro.getText(),
            (String) cbMaterial.getSelectedItem(), txtTamanhoQuadro.getText(),
            (String) cbFaixaEtaria.getSelectedItem(), (String) cbFinalidade.getSelectedItem() // Novos params
        );

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Produto salvo!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar.");
        }
    }
}