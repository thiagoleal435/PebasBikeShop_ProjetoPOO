package br.com.bikeshop.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuPrincipal() {
        super("Peba's Bike Shop - Sistema de Gestão");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza
        
        // Vamos maximizar a janela ao abrir para ficar bonito
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        criarMenu();
    }

	private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Cadastros (Já existente)
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenuItem itemCliente = new JMenuItem("Clientes");
        itemCliente.addActionListener(e -> new TelaListaClientes().setVisible(true));
        JMenuItem itemProduto = new JMenuItem("Produtos / Estoque");
        itemProduto.addActionListener(e -> new TelaListaProdutos().setVisible(true));
        menuCadastros.add(itemCliente);
        menuCadastros.add(itemProduto);

        // --- MENU VENDAS (Atualizado) ---
        JMenu menuVendas = new JMenu("Vendas");
        
        JMenuItem itemNovaVenda = new JMenuItem("Nova Venda");
        itemNovaVenda.addActionListener(e -> new TelaSelecaoVenda().setVisible(true));

        // NOVO ITEM
        JMenuItem itemHistorico = new JMenuItem("Histórico de Vendas");
        itemHistorico.addActionListener(e -> new TelaHistoricoVendas().setVisible(true));

        // Item Métricas (AGORA IMPLEMENTADO)
        JMenuItem itemMetricas = new JMenuItem("Métricas / Relatórios");
        itemMetricas.addActionListener(e -> {
            new TelaMetricas().setVisible(true);
        });

        menuVendas.add(itemNovaVenda);
        menuVendas.add(itemHistorico); // Adiciona o histórico
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

}
