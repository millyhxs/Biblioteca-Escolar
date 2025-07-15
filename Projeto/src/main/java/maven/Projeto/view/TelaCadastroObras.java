package maven.Projeto.view;

import maven.Projeto.controller.ObraController;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Artigo;
import maven.Projeto.model.Livro;
import maven.Projeto.model.Revista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaCadastroObras extends JFrame {
    private JComboBox<String> tipoCombo;
    private JTextField codField, tituloField, autorField, anoField;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    public TelaCadastroObras() {
    	setResizable(false);
        setTitle("Cadastro de Obras");
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
        
        JLabel titulo = new JLabel("Cadastro de Obras", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 10, 700, 30);
        painel.add(titulo);
        
        JLabel tipoLabel = criarLabel("Tipo:", 30, 60);
        painel.add(tipoLabel);
        tipoCombo = new JComboBox<>(new String[]{"Livro", "Revista", "Artigo"});
        tipoCombo.setBounds(100, 60, 150, 25);
        painel.add(tipoCombo);
        
        painel.add(criarLabel("Código:", 270, 60));
        codField = criarCampoTexto(340, 60);
        painel.add(codField);
        
        painel.add(criarLabel("Título:", 30, 100));
        tituloField = criarCampoTexto(100, 100);
        painel.add(tituloField);
        
        painel.add(criarLabel("Autor:", 270, 100));
        autorField = criarCampoTexto(340, 100);
        painel.add(autorField);
        
        painel.add(criarLabel("Ano:", 510, 100));
        anoField = criarCampoTexto(550, 100);
        painel.add(anoField);
        
        JButton excluirBtn = new JButton("Excluir Obra");
        excluirBtn.setBounds(200, 140, 160, 30);
        painel.add(excluirBtn);
        
        JButton adicionarBtn = new JButton("Adicionar Obra");
        adicionarBtn.setBounds(30, 140, 160, 30);
        painel.add(adicionarBtn);
        
        adicionarBtn.addActionListener(e -> {
            try {
                String tipo = (String) tipoCombo.getSelectedItem();
                String codigo = codField.getText();
                String tituloTxt = tituloField.getText();
                String autor = autorField.getText();
                String ano = anoField.getText();
                
                ObraController.verificacaoDeDados(tipo, codigo, tituloTxt, autor, ano, false);
                
                for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                    String codigoExistente = (String) modeloTabela.getValueAt(i, 0); 
                    if (codigoExistente.equals(codigo)) {
                        JOptionPane.showMessageDialog(this,
                            "Já existe uma obra com esse código.",
                            "Código Duplicado",
                            JOptionPane.WARNING_MESSAGE);
                        return; 
                    }
                }
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Obra adicionada com sucesso!");
                
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        modeloTabela = new DefaultTableModel(new String[]{"Código", "Título", "Autor", "Ano", "Tipo"}, 0);
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
            
            String codigo = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            String tipo = (String) modeloTabela.getValueAt(linhaSelecionada, 4);
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir essa obra?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
					ObraController.exclusaoDeDados(tipo, codigo);
				} catch (CampoVazioException e1) {
					JOptionPane.showMessageDialog(this, "Código e Tipo inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
                
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Obra excluída com sucesso!");
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
        
        for (Livro livro : ObraController.getLivros()) {
            modeloTabela.addRow(new Object[]{
                livro.getCodigo(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAnoDePublicacao(),
                "Livro"
            });
        }
        
        for (Revista revista : ObraController.getRevistas()) {
            modeloTabela.addRow(new Object[]{
                revista.getCodigo(),
                revista.getTitulo(),
                revista.getAutor(),
                revista.getAnoDePublicacao(),
                "Revista"
            });
        }
        
        for (Artigo artigo : ObraController.getArtigos()) {
            modeloTabela.addRow(new Object[]{
                artigo.getCodigo(),
                artigo.getTitulo(),
                artigo.getAutor(),
                artigo.getAnoDePublicacao(),
                "Artigo"
            });
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroObras().setVisible(true));
    }
}
