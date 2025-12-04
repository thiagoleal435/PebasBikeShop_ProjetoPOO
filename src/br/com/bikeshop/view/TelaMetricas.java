package br.com.bikeshop.view;

import br.com.bikeshop.controller.RelatorioController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Map;

public class TelaMetricas extends JFrame {

    private RelatorioController controller;

    public TelaMetricas() {
        super("Métricas e Indicadores de Desempenho");
        this.controller = new RelatorioController();

        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Painel Principal com Scroll (caso a tela fique pequena)
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(painelPrincipal);
        add(scroll);

        // --- Adicionando os Cards das Métricas ---
        
        // 1. Vendas do Mês
        painelPrincipal.add(criarCardMes());
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10))); // Espaçamento

        // 2. Produto Mais Vendido
        painelPrincipal.add(criarCardProduto("Produto Mais Vendido (Campeão)", true));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        // 3. Melhor Cliente
        painelPrincipal.add(criarCardCliente());
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        // 4. Produto Menos Vendido
        painelPrincipal.add(criarCardProduto("Produto Menos Vendido (Atenção)", false));
        painelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));

        // 5. Melhor Dia
        painelPrincipal.add(criarCardDia());
    }

    // --- MÉTODOS AUXILIARES PARA CRIAR O DESIGN ---

    private JPanel criarPainelBase(String titulo) {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(0, 2, 5, 5)); // 2 Colunas
        p.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY), titulo, TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        return p;
    }

    private JPanel criarCardMes() {
        JPanel p = criarPainelBase("1. Total de Vendas do Mês Atual");
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel lblDados = new JLabel(controller.getMetricaMesAtual());
        lblDados.setFont(new Font("Arial", Font.BOLD, 16));
        lblDados.setForeground(new Color(0, 100, 0)); // Verde escuro
        p.add(lblDados);
        return p;
    }

    private JPanel criarCardProduto(String titulo, boolean isMaisVendido) {
        JPanel p = criarPainelBase("2. " + titulo);
        Map<String, String> dados = isMaisVendido ? controller.getProdutoMaisVendido() : controller.getProdutoMenosVendido();

        if (dados == null) {
            p.add(new JLabel("Sem dados de vendas."));
            return p;
        }

        p.add(new JLabel("Descrição:"));
        p.add(new JLabel(dados.get("descricao")));
        
        p.add(new JLabel("Quantidade Vendida:"));
        p.add(new JLabel(dados.get("qtd")));
        
        p.add(new JLabel("Valor Total Arrecadado:"));
        p.add(new JLabel(dados.get("total")));
        
        return p;
    }

    private JPanel criarCardCliente() {
        JPanel p = criarPainelBase("3. Melhor Cliente da Loja");
        Map<String, Object> dados = controller.getMelhorCliente();

        if (dados == null) {
            p.add(new JLabel("Sem dados de vendas."));
            return p;
        }

        p.add(new JLabel("Nome:"));
        p.add(new JLabel((String) dados.get("nome")));
        
        p.add(new JLabel("CPF:"));
        p.add(new JLabel((String) dados.get("cpf")));
        
        p.add(new JLabel("Itens Comprados:"));
        p.add(new JLabel(dados.get("qtdItens").toString()));
        
        p.add(new JLabel("Total Gasto:"));
        p.add(new JLabel((String) dados.get("totalGasto")));
        
        p.add(new JLabel("Comprovantes Relacionados:"));
        String[] faturas = (String[]) dados.get("faturas");
        JComboBox<String> cbFaturas = new JComboBox<>(faturas);
        p.add(cbFaturas);

        return p;
    }

    private JPanel criarCardDia() {
        JPanel p = criarPainelBase("5. Dia com Mais Vendas");
        Map<String, Object> dados = controller.getMelhorDia();

        if (dados == null) {
            p.add(new JLabel("Sem dados de vendas."));
            return p;
        }

        p.add(new JLabel("Data:"));
        p.add(new JLabel((String) dados.get("data")));
        
        p.add(new JLabel("Valor Total do Dia:"));
        p.add(new JLabel((String) dados.get("total")));
        
        p.add(new JLabel("Item Mais Vendido do Dia:"));
        p.add(new JLabel((String) dados.get("itemTop")));
        
        p.add(new JLabel("Comprovantes Emitidos:"));
        String[] faturas = (String[]) dados.get("faturas");
        JComboBox<String> cbFaturas = new JComboBox<>(faturas);
        p.add(cbFaturas);

        return p;
    }
}