package br.com.bikeshop.app;

import br.com.bikeshop.view.MenuPrincipal;
import br.com.bikeshop.view.TelaListaClientes;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Agora chamamos o Menu Principal
            new MenuPrincipal().setVisible(true);
        });
    }
}