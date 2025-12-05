package br.com.bikeshop.view;

import br.com.bikeshop.model.Venda;
import javax.swing.*;
import java.awt.*;

public class TelaFatura extends JDialog {

    public TelaFatura(Frame parent, Venda venda) {
        super(parent, "Comprovante de Venda", true);
        setSize(400, 700);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        painel.setBackground(Color.WHITE);

        // Estilo Fonte Monospaced (parece cupom fiscal)
        Font fonteCupom = new Font("Monospaced", Font.PLAIN, 14);

        addLabel(painel, "================================", fonteCupom);
        addLabel(painel, "       PEBA'S BIKE SHOP         ", new Font("Monospaced", Font.BOLD, 16));
        addLabel(painel, "================================", fonteCupom);
        addLabel(painel, " ", fonteCupom);
        addLabel(painel, "FATURA Nº: " + venda.getCodigoUnico(), fonteCupom);
        addLabel(painel, "DATA: " + venda.getData(), fonteCupom);
        addLabel(painel, "--------------------------------", fonteCupom);
        addLabel(painel, "CLIENTE:", new Font("Monospaced", Font.BOLD, 14));
        addLabel(painel, venda.getCliente().getNome(), fonteCupom);
        addLabel(painel, "CPF: " + venda.getCliente().getCpf(), fonteCupom);
        addLabel(painel, "--------------------------------", fonteCupom);
        addLabel(painel, "PRODUTO:", new Font("Monospaced", Font.BOLD, 14));
        addLabel(painel, venda.getProduto().getDescricao(), fonteCupom);
        addLabel(painel, "QTD: " + venda.getQuantidade(), fonteCupom);
        
     // Mostra Pagamento
        addLabel(painel, "PAGAMENTO: " + venda.getMetodoPagamento(), fonteCupom);
        if ("Pix".equalsIgnoreCase(venda.getMetodoPagamento())) {
            addLabel(painel, "(Desconto de 10% aplicado)", new Font("Monospaced", Font.ITALIC, 12));
        }

        addLabel(painel, "--------------------------------", fonteCupom);
        addLabel(painel, String.format("TOTAL: R$ %.2f", venda.getValorTotal()), new Font("Monospaced", Font.BOLD, 18));
        addLabel(painel, "--------------------------------", fonteCupom);
        addLabel(painel, " ", fonteCupom);
        addLabel(painel, "||||||||||||||||||||||||||||||||", fonteCupom); // Código de barras fake
        addLabel(painel, "   " + venda.getCodigoUnico() + "   ", fonteCupom);

        add(painel, BorderLayout.CENTER);

        JButton btnOk = new JButton("Imprimir / Fechar");
        btnOk.addActionListener(e -> dispose());
        add(btnOk, BorderLayout.SOUTH);
    }

    private void addLabel(JPanel p, String text, Font f) {
        JLabel l = new JLabel(text);
        l.setFont(f);
        l.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(l);
        p.add(Box.createRigidArea(new Dimension(0, 5)));
    }
}