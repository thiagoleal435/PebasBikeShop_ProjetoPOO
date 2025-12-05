package br.com.bikeshop.view;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        super("Peba's Bike Shop - Sistema de Gestão");
        
        // Configurações da Janela
        setSize(1024, 768); // Tamanho inicial bom
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Abre em tela cheia
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Define o nosso Painel com Imagem como o conteúdo principal da janela
        PainelFundo painelPrincipal = new PainelFundo();
        painelPrincipal.setLayout(new GridBagLayout()); // Layout para centralizar o texto
        setContentPane(painelPrincipal);

        // 2. Cria os Textos (Labels)
        criarConteudoVisual(painelPrincipal);

        // 3. Cria o Menu (Mantemos o que já existia)
        criarMenu();
    }

    private void criarConteudoVisual(JPanel painel) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Margem entre os elementos

        // TÍTULO GRANDE
        JLabel lblTitulo = new JLabel("PEBA'S BIKE SHOP");
        lblTitulo.setFont(new Font("Arial Black", Font.BOLD, 60)); // Fonte grossa e grande
        lblTitulo.setForeground(Color.WHITE); // Texto Branco
        
        // Efeito de Sombra no Título (Opcional, mas fica bonito)
        // lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 0)); 
        
        gbc.gridy = 0;
        painel.add(lblTitulo, gbc);

        // SUBTÍTULO
        JLabel lblSubtitulo = new JLabel("A Melhor Loja de Bicicletas do Nordeste");
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 28));
        lblSubtitulo.setForeground(new Color(255, 215, 0)); // Cor Dourada/Amarela
        
        gbc.gridy = 1;
        painel.add(lblSubtitulo, gbc);

        // DESCRIÇÃO / SLOGAN
        JLabel lblDescricao = new JLabel("Qualidade, Performance e estilo Peba de ser.");
        lblDescricao.setFont(new Font("SansSerif", Font.ITALIC, 18));
        lblDescricao.setForeground(Color.LIGHT_GRAY);
        
        gbc.gridy = 2;
        painel.add(lblDescricao, gbc);

        // BOTÃO DE ACÃO RÁPIDA (Opcional)
        JButton btnIniciar = new JButton("Nova Venda Agora");
        btnIniciar.setFont(new Font("Arial", Font.BOLD, 16));
        btnIniciar.setBackground(new Color(255, 140, 0)); // Laranja
        btnIniciar.setForeground(Color.WHITE);
        btnIniciar.setFocusPainted(false);
        btnIniciar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnIniciar.addActionListener(e -> new TelaSelecaoVenda().setVisible(true));

        gbc.gridy = 3;
        gbc.insets = new Insets(40, 10, 10, 10); // Mais espaço antes do botão
        painel.add(btnIniciar, gbc);
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Cadastros
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenuItem itemCliente = new JMenuItem("Clientes");
        itemCliente.addActionListener(e -> new TelaListaClientes().setVisible(true));
        JMenuItem itemProduto = new JMenuItem("Produtos / Estoque");
        itemProduto.addActionListener(e -> new TelaListaProdutos().setVisible(true));
        menuCadastros.add(itemCliente);
        menuCadastros.add(itemProduto);

        // Menu Vendas
        JMenu menuVendas = new JMenu("Vendas");
        JMenuItem itemNovaVenda = new JMenuItem("Nova Venda");
        itemNovaVenda.addActionListener(e -> new TelaSelecaoVenda().setVisible(true));
        JMenuItem itemHistorico = new JMenuItem("Histórico de Vendas");
        itemHistorico.addActionListener(e -> new TelaHistoricoVendas().setVisible(true));
        JMenuItem itemMetricas = new JMenuItem("Métricas / Relatórios");
        itemMetricas.addActionListener(e -> new TelaMetricas().setVisible(true));
        menuVendas.add(itemNovaVenda);
        menuVendas.add(itemHistorico);
        menuVendas.add(itemMetricas);

        // Menu Sair
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));
        menuSair.add(itemSair);

        menuBar.add(menuCadastros);
        menuBar.add(menuVendas);
        menuBar.add(menuSair);
        setJMenuBar(menuBar);
    }

    // --- CLASSE INTERNA PARA DESENHAR O FUNDO ---
    private class PainelFundo extends JPanel {
        private Image imagemFundo;

        public PainelFundo() {
            // Tenta carregar a imagem
            try {
                // Ajuste o caminho se necessário (ex: "/imagens/fundo.jpg")
                URL urlImagem = getClass().getResource("/imagens/fundo.jpg");
                if (urlImagem != null) {
                    imagemFundo = new ImageIcon(urlImagem).getImage();
                } else {
                    System.err.println("Imagem de fundo não encontrada em /imagens/fundo.jpg");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Limpa o painel

            // 1. Desenha a imagem ocupando toda a tela
            if (imagemFundo != null) {
                g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Se não achar a imagem, pinta de cinza escuro
                g.setColor(Color.DARK_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            // 2. Desenha a camada escura semi-transparente (O Efeito "Opaco")
            // A cor preta (0,0,0) com alpha 150 (de 0 a 255) cria o escurecimento
            g.setColor(new Color(0, 0, 0, 150)); 
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}