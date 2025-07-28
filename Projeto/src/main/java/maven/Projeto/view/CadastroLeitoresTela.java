package maven.Projeto.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import maven.Projeto.controller.LeitoresController;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Leitor;

public class CadastroLeitoresTela extends JFrame {
	private JComboBox<String> tipoCombo;
    private JTextField matriculaField, nomeField, telefoneField, emailField;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    LeitoresController leitoresController = new LeitoresController();
    
	public CadastroLeitoresTela() {
		setResizable(false);
        setTitle("Cadastro de Leitores");
		setSize(740, 560);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
		
		JPanel painel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(45, 45, 45));
            }
        };
        
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Cadastro de Leitores", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 10, 700, 30);
        painel.add(titulo);
        
        JLabel tipoLabel = criarLabel("Tipo:", 30, 60);
        painel.add(tipoLabel);
        tipoCombo = new JComboBox<>(new String[]{"Aluno", "Professor", "Servidor"});
        tipoCombo.setBounds(100, 60, 150, 25);
        painel.add(tipoCombo);
        
        painel.add(criarLabel("Matricula:", 270, 60));
        matriculaField = criarCampoTexto(340, 60);
        painel.add(matriculaField);
        
        painel.add(criarLabel("Nome:", 30, 100));
        nomeField = criarCampoTexto(100, 100);
        painel.add(nomeField);
        
        painel.add(criarLabel("Telefone:", 270, 100));
        telefoneField = criarCampoTexto(340, 100);
        painel.add(telefoneField);
        
        painel.add(criarLabel("Email:", 510, 60));
        emailField = criarCampoTexto(550, 60);
        painel.add(emailField);
        
        JButton excluirBtn = new JButton("Excluir Leitor");
        excluirBtn.setBounds(470, 150, 160, 30);
        painel.add(excluirBtn);
        
        JButton adicionarBtn = new JButton("Adicionar Leitor");
        adicionarBtn.setBounds(110, 150, 160, 30);
        painel.add(adicionarBtn);
        
        JButton editarBtn = new JButton("Editar Leitor");
        editarBtn.setBounds(290, 150, 160, 30);
        painel.add(editarBtn);
        
        adicionarBtn.addActionListener(e -> {
            try {
                String matricula = matriculaField.getText();
                String nomeTxt = nomeField.getText();
                String telefone = telefoneField.getText();
                String email = emailField.getText();
                String tipo = (String) tipoCombo.getSelectedItem();
                
                
				leitoresController.verificacaoDeDados(tipo, matricula, nomeTxt, telefone, email);
                
                for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                    String matriculaExistente = (String) modeloTabela.getValueAt(i, 0); 
                    if (matriculaExistente.equals(matricula)) {
                        JOptionPane.showMessageDialog(this,
                            "Já existe uma leitor com essa matricula.",
                            "Matricula Duplicada",
                            JOptionPane.WARNING_MESSAGE);
                        return; 
                    }
                }
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Leitor adicionado com sucesso!");
                
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        modeloTabela = new DefaultTableModel(new String[]{"Matrícula", "Nome", "Telefone", "Email", "Tipo"}, 0) {
        	@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.setAutoCreateRowSorter(true);
        tabela.setRowSelectionAllowed(true);
        tabela.getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 200, 670, 270);
        painel.add(scroll);
        
        atualizarTabela();
        
        excluirBtn.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um leitor para excluir.");
                return;
            }
            
            String matricula = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir essa leitor?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
					leitoresController.exclusaoDeDados(matricula);
				} catch (CampoVazioException e1) {
					JOptionPane.showMessageDialog(this, "Matrícula e Tipo inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
                
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Leitor excluído com sucesso!");
            }
        });
        
        editarBtn.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um leitor para editar.");
                return;
            }
            
            String matriculaAntiga = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            String matricula = matriculaField.getText();
            String nome = nomeField.getText();
            String telefone = telefoneField.getText();
            String email = emailField.getText();
            String tipo = (String) tipoCombo.getSelectedItem();
            
            try {
                leitoresController.editarUsuario(matriculaAntiga, matricula, nome, tipo, telefone, email);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Leitor atualizado com sucesso!");
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        
        });
        
        JButton btnVoltar = new JButton("Voltar à Área do Administrador");
        btnVoltar.setBounds(230, 480, 250, 30);
        painel.add(btnVoltar);

        btnVoltar.addActionListener(e -> {
            dispose();
            new AdministradorTela().setVisible(true);
        });
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
        
        for (Leitor leitor : leitoresController.getLeitores()) {
            modeloTabela.addRow(new Object[]{
            	leitor.getMatricula(),
            	leitor.getNome(),
            	leitor.getTelefone(),
            	leitor.getEmail(),
                leitor.getTipoDeUsuario()
            });
        }
    }
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
            new CadastroLeitoresTela().setVisible(true);
		});
	}
}
