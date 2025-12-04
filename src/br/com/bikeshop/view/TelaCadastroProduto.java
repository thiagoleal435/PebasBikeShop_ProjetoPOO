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
    
    // ATENÇÃO: txtMaterial FOI REMOVIDO PARA EVITAR O ERRO NULLPOINTER
    private JTextField txtAro, txtTamanhoQuadro;
    private JComboBox<String> cbMaterial; 
    
    private ProdutoController controller;
    private int indiceEdicao;

    public TelaCadastroProduto(Frame parent, ProdutoController controller, int indice, Produto produto) {
        super(parent, true);
        this.controller = controller;
        this.indiceEdicao = indice;
        
        setTitle(produto == null ? "Novo Produto" : "Editar Produto");
        setSize(500, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        
        JPanel painelForm = new JPanel();
        painelForm.setLayout(new GridLayout(10, 2, 10, 10));
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
        txtDescricao = new JTextField(); 
        painelForm.add(txtDescricao);

        // 4. Preço
        painelForm.add(new JLabel("Preço (R$):"));
        txtPreco = new JTextField(); 
        painelForm.add(txtPreco);

        // 5. Estoque
        painelForm.add(new JLabel("Estoque Atual:"));
        txtEstoque = new JTextField("0"); 
        painelForm.add(txtEstoque);

        // 6. Estoque Mínimo
        painelForm.add(new JLabel("Estoque Mínimo (Referência):"));
        txtEstoqueMin = new JTextField("5");
        txtEstoqueMin.setEditable(false);
        txtEstoqueMin.setBackground(new Color(245, 245, 220));
        painelForm.add(txtEstoqueMin);

        // 7. Aro
        painelForm.add(new JLabel("Aro (Bikes/Rodas):"));
        txtAro = new JTextField(); 
        painelForm.add(txtAro);

        // 8. Material
        painelForm.add(new JLabel("Material (Bikes/Quadros):"));
        String[] materiais = {"Alumínio", "Ferro", "Carbono"};
        cbMaterial = new JComboBox<>(materiais);
        painelForm.add(cbMaterial);

        // 9. Tamanho
        painelForm.add(new JLabel("Tamanho (Quadro/Bike):"));
        txtTamanhoQuadro = new JTextField(); 
        painelForm.add(txtTamanhoQuadro);
        
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

        if (produto != null) {
            preencherCampos(produto);
        } else {
            txtCodigo.setText(controller.gerarProximoCodigo());
        }
        
        // Garante que a visualização inicial esteja correta
        atualizarCamposVisuais();
    }

    private void atualizarCamposVisuais() {
        String tipo = (String) cbTipo.getSelectedItem();
        
        boolean isBike = "Bicicleta".equals(tipo);
        boolean isQuadro = "Quadro".equals(tipo);
        boolean isRoda = "Roda".equals(tipo);

        // --- LÓGICA CORRIGIDA ---

        // 1. ARO: Habilitado apenas para Bicicleta OU Roda
        txtAro.setEnabled(isBike || isRoda);
        if (!txtAro.isEnabled()) txtAro.setText("");

        // 2. MATERIAL: Habilitado apenas para Bicicleta OU Quadro
        cbMaterial.setEnabled(isBike || isQuadro);

        // 3. TAMANHO: Habilitado apenas para Bicicleta OU Quadro
        // (Freio, Selim, etc, ficarão desabilitados aqui)
        txtTamanhoQuadro.setEnabled(isBike || isQuadro);
        if (!txtTamanhoQuadro.isEnabled()) txtTamanhoQuadro.setText("");
        
        // Se for Roda, bloqueia Tamanho (pois Roda usa Aro)
        if (isRoda) txtTamanhoQuadro.setEnabled(false);
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
            
        } else if (p instanceof Peca) {
            Peca peca = (Peca) p;
            String categoria = peca.getCategoria();
            cbTipo.setSelectedItem(categoria);
            
            // Lógica de recuperação dos dados
            if ("Quadro".equals(categoria)) {
                txtTamanhoQuadro.setText(peca.getMedida());
                if (peca.getMaterial() != null) {
                    cbMaterial.setSelectedItem(peca.getMaterial());
                }
            } 
            else if ("Roda".equals(categoria)) {
                txtAro.setText(peca.getMedida());
            } 
            // Outras peças não usam esses campos, então não preenchemos nada específico
        }
    }

    private void salvar() {
        try {
            int estoqueAtual = Integer.parseInt(txtEstoque.getText());
            int estoqueMinimo = Integer.parseInt(txtEstoqueMin.getText());

            if (estoqueAtual <= estoqueMinimo) {
                JOptionPane.showMessageDialog(this, 
                    "REGRA DE NEGÓCIO:\nO Estoque Atual (" + estoqueAtual + ") " +
                    "deve ser MAIOR que o Estoque Mínimo (" + estoqueMinimo + ").",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Verifique os números do estoque.");
            return;
        }

        boolean sucesso = controller.salvarOuAtualizar(
            indiceEdicao,
            (String) cbTipo.getSelectedItem(),
            txtCodigo.getText(),
            txtDescricao.getText(),
            txtPreco.getText(),
            txtEstoque.getText(),
            txtEstoqueMin.getText(),
            txtAro.getText(),
            (String) cbMaterial.getSelectedItem(),
            txtTamanhoQuadro.getText()
        );

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Produto salvo!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar.");
        }
    }
}