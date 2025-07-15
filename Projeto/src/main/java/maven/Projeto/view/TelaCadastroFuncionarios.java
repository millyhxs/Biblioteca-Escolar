package maven.Projeto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Funcionario;

public class TelaCadastroFuncionarios extends JFrame {
    private JComboBox<String> nivelCombo;
    private JTextField idField, nomeField;
    private JPasswordField senhaField;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public TelaCadastroFuncionarios() {
        setResizable(false);
        setTitle("Cadastro de Funcionários");
        setSize(740, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(45, 45, 45));
            }
        };

        getContentPane().add(painel);

        JLabel titulo = new JLabel("Cadastro de Funcionários", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 10, 700, 30);
        painel.add(titulo);

        painel.add(criarLabel("ID:", 30, 60));
        idField = criarCampoTexto(100, 60);
        painel.add(idField);

        painel.add(criarLabel("Nome:", 270, 60));
        nomeField = criarCampoTexto(340, 60);
        painel.add(nomeField);

        painel.add(criarLabel("Senha:", 30, 100));
        senhaField = new JPasswordField();
        senhaField.setBounds(100, 100, 150, 25);
        painel.add(senhaField);

        painel.add(criarLabel("Nível:", 270, 100));
        nivelCombo = new JComboBox<>(new String[]{"Administrador", "Bibliotecário", "Estagiário"});
        nivelCombo.setBounds(340, 100, 150, 25);
        painel.add(nivelCombo);

        JButton adicionarBtn = new JButton("Adicionar Funcionário");
        adicionarBtn.setBounds(30, 140, 200, 30);
        painel.add(adicionarBtn);

        JButton excluirBtn = new JButton("Excluir Funcionário");
        excluirBtn.setBounds(250, 140, 200, 30);
        painel.add(excluirBtn);

        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Nível de Acesso"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 200, 670, 230);
        painel.add(scroll);

        adicionarBtn.addActionListener(e -> {
            String id = idField.getText();
            String nome = nomeField.getText();
            String senha = new String(senhaField.getPassword());
            String nivel = (String) nivelCombo.getSelectedItem();

            try {
                FuncionarioController.verificarCadastro(id, nome, senha, nivel);
                JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
                atualizarTabela();
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        excluirBtn.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário para excluir.");
                return;
            }

            String id = (String) modeloTabela.getValueAt(linha, 0);

            int confirmar = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir esse funcionário?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmar == JOptionPane.YES_OPTION) {
                try {
                    FuncionarioController.excluirFuncionario(id);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                } catch (CampoVazioException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        atualizarTabela();
    }

    private JLabel criarLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, 100, 25);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField criarCampoTexto(int x, int y) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, 150, 25);
        return campo;
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        
        for (Funcionario f : FuncionarioController.getFuncionarios()) {
            modeloTabela.addRow(new Object[]{f.getId(), f.getNome(), f.getTipo()});
        }

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            tabela.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }

        tabela.setAutoCreateRowSorter(true);
        tabela.getRowSorter().toggleSortOrder(0); 
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroFuncionarios().setVisible(true));
    }
}
