package br.com.bikeshop.view;

import br.com.bikeshop.controller.VendaController;
import br.com.bikeshop.model.Cliente;
import br.com.bikeshop.model.Produto;
import br.com.bikeshop.model.Venda;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TelaDadosVenda extends JDialog {

    private JComboBox<Cliente> cbClientes;
    private JTextField txtQtd, txtData;
    private VendaController controller;
    private Produto produto;

    public TelaDadosVenda(Window parent, VendaController controller, Produto produto) {
        super(parent, Dialog.ModalityType.APPLICATION_MODAL); // Garante que bloqueie a tela de trás
        this.controller = controller;
        this.produto = produto;

        setTitle("Finalizar Venda: " + produto.getDescricao());
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Cliente:"));
        cbClientes = new JComboBox<>();
        carregarClientes();
        add(cbClientes);

        add(new JLabel("Preço Unitário:"));
        JTextField txtPreco = new JTextField("R$ " + produto.getPreco());
        txtPreco.setEditable(false);
        add(txtPreco);

        add(new JLabel("Quantidade:"));
        txtQtd = new JTextField("1");
        add(txtQtd);

        add(new JLabel("Data (dd/MM/yyyy):"));
        String hoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        txtData = new JTextField(hoje);
        add(txtData);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        JButton btnFinalizar = new JButton("FINALIZAR VENDA");
        btnFinalizar.setBackground(Color.ORANGE);
        btnFinalizar.addActionListener(e -> finalizarVenda());
        add(btnFinalizar);
    }

    private void carregarClientes() {
        List<Cliente> lista = controller.listarClientes();
        for (Cliente c : lista) {
            cbClientes.addItem(c);
        }
    }

    private void finalizarVenda() {
        try {
            // 1. Validação da Data (Requisito 2)
            String dataTexto = txtData.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // Não aceita datas como 32/01/2025
            
            Date dataVenda = sdf.parse(dataTexto);
            Date dataHoje = new Date(); // Data com hora atual

            // Zera as horas para comparar apenas os dias
            SimpleDateFormat fmtSemHora = new SimpleDateFormat("yyyyMMdd");
            if (Integer.parseInt(fmtSemHora.format(dataVenda)) > Integer.parseInt(fmtSemHora.format(dataHoje))) {
                JOptionPane.showMessageDialog(this, "ERRO: A data da venda não pode ser futura!", "Data Inválida", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Coleta dados normais
            int qtd = Integer.parseInt(txtQtd.getText());
            Cliente cliente = (Cliente) cbClientes.getSelectedItem();

            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente!");
                return;
            }

            // 3. Realiza a Venda
            Venda vendaFeita = controller.realizarVenda(cliente, produto, qtd, dataTexto);
            
            // Sucesso! Pode fechar essa janela
            dispose(); 

            // 4. Verificação de Estoque Mínimo (Requisito 3)
            // O produto dentro de 'vendaFeita' já está com o estoque atualizado
            int estoqueAtual = vendaFeita.getProduto().getEstoqueAtual();
            int estoqueMinimo = vendaFeita.getProduto().getEstoqueMinimo();

            if (estoqueAtual <= estoqueMinimo) {
                String msg = "ALERTA DE ESTOQUE!\n\n" +
                             "O produto '" + vendaFeita.getProduto().getDescricao() + "'\n" +
                             "atingiu o nível crítico.\n" +
                             "Estoque Atual: " + estoqueAtual + "\n" +
                             "Mínimo Definido: " + estoqueMinimo;
                
                JOptionPane.showMessageDialog(null, msg, "Aviso de Reposição", JOptionPane.WARNING_MESSAGE);
            }

            // 5. Mostra a Fatura
            new TelaFatura(null, vendaFeita).setVisible(true);

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dia/mês/ano (ex: 04/12/2025).");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage()); // Erro de estoque insuficiente
        }
    }
}