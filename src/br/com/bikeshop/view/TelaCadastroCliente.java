package br.com.bikeshop.view;

import br.com.bikeshop.controller.ClienteController;
import br.com.bikeshop.model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastroCliente extends JDialog {

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtTelefone;
    private ClienteController controller;
    
    // Variáveis para controlar a edição
    private int indiceEdicao = -1; 

    // Construtor ÚNICO (Serve tanto para novo quanto para editar)
    public TelaCadastroCliente(Frame parent, ClienteController controller, int indice, Cliente clienteParaEditar) {
        super(parent, true); // Modal
        this.controller = controller;
        this.indiceEdicao = indice;

        // Configuração da Janela
        setSize(400, 300); // TAMANHO FIXO PARA NÃO FICAR MINÚSCULA
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Título dinâmico
        if (clienteParaEditar != null) {
            setTitle("Editar Cliente");
        } else {
            setTitle("Novo Cliente");
        }

        // Criando componentes
        add(new JLabel("Nome:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        add(txtCpf);

        add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        add(txtTelefone);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        add(btnCancelar);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarOuAtualizar());
        add(btnSalvar);

        // --- A MÁGICA DA EDIÇÃO ACONTECE AQUI ---
        // Se passamos um cliente, preenchemos os campos automaticamente
        if (clienteParaEditar != null) {
            txtNome.setText(clienteParaEditar.getNome());
            txtCpf.setText(clienteParaEditar.getCpf());
            txtTelefone.setText(clienteParaEditar.getTelefone());
        }
    }

    private void salvarOuAtualizar() {
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String telefone = txtTelefone.getText();

        boolean sucesso;

        if (this.indiceEdicao == -1) {
            // Se índice é -1, é um NOVO cadastro
            sucesso = controller.salvar(nome, cpf, telefone);
        } else {
            // Se tem índice, é uma ATUALIZAÇÃO
            sucesso = controller.atualizar(this.indiceEdicao, nome, cpf, telefone);
        }

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar. Verifique os campos.");
        }
    }
}