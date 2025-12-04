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

        // --- MENU CADASTROS ---
        JMenu menuCadastros = new JMenu("Cadastros");
        
        // Item: Clientes
        JMenuItem itemCliente = new JMenuItem("Clientes");
        itemCliente.addActionListener(e -> {
            // Abre a tela de lista de clientes que fizemos antes
            new TelaListaClientes().setVisible(true);
            // Não fechamos o menu principal, apenas abrimos a outra tela por cima
        });

        // Item: Produtos
        JMenuItem itemProduto = new JMenuItem("Produtos / Bicicletas");
        itemProduto.addActionListener(e -> {
        	new TelaListaProdutos().setVisible(true);
             // Futuramente: new TelaListaProdutos().setVisible(true);
        });

        menuCadastros.add(itemCliente);
        menuCadastros.add(itemProduto);

        // --- MENU VENDAS (Fase 4) ---
        JMenu menuVendas = new JMenu("Vendas");
        JMenuItem itemNovaVenda = new JMenuItem("Nova Venda");
        menuVendas.add(itemNovaVenda);

        // --- MENU SAIR ---
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Fechar Sistema");
        itemSair.addActionListener(e -> System.exit(0));
        menuSair.add(itemSair);

        // Adiciona os menus na barra
        menuBar.add(menuCadastros);
        menuBar.add(menuVendas);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

}
