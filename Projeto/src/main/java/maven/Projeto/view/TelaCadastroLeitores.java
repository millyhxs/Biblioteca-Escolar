package maven.Projeto.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import maven.Projeto.controller.LeitoresController;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Leitor;

public class TelaCadastroLeitores extends JFrame {
	private JComboBox<String> tipoCombo;
    private JTextField codField, tituloField, autorField, anoField;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
	public TelaCadastroLeitores() {
		setResizable(false);
        setTitle("Cadastro de Leitores");
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
        codField = criarCampoTexto(340, 60);
        painel.add(codField);
        
        painel.add(criarLabel("Nome:", 30, 100));
        tituloField = criarCampoTexto(100, 100);
        painel.add(tituloField);
        
        painel.add(criarLabel("Telefone:", 270, 100));
        autorField = criarCampoTexto(340, 100);
        painel.add(autorField);
        
        painel.add(criarLabel("Email:", 510, 100));
        anoField = criarCampoTexto(550, 100);
        painel.add(anoField);
        
        JButton excluirBtn = new JButton("Excluir Leitor");
        excluirBtn.setBounds(200, 140, 160, 30);
        painel.add(excluirBtn);
        
        JButton adicionarBtn = new JButton("Adicionar Leitor");
        adicionarBtn.setBounds(30, 140, 160, 30);
        painel.add(adicionarBtn);
        
        adicionarBtn.addActionListener(e -> {
            try {
                String matricula = codField.getText();
                String nomeTxt = tituloField.getText();
                String telefone = autorField.getText();
                String email = anoField.getText();
                String tipo = (String) tipoCombo.getSelectedItem();
                
                LeitoresController.verificacaoDeDados(tipo, matricula, nomeTxt, telefone, email);
                
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
        
        modeloTabela = new DefaultTableModel(new String[]{"Matrícula", "Nome", "Telefone", "Email", "Tipo"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 200, 670, 230);
        painel.add(scroll);
        
        atualizarTabela();
        
        excluirBtn.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma obra para excluir.");
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
					LeitoresController.exclusaoDeDados(matricula);
				} catch (CampoVazioException e1) {
					JOptionPane.showMessageDialog(this, "Matrícula e Tipo inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
                
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Leitor excluída com sucesso!");
            }
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
        
        for (Leitor leitor : LeitoresController.getLeitores()) {
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
            new TelaCadastroLeitores().setVisible(true);
		});
	}
}
