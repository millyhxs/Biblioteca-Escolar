package maven.Projeto.view;

import maven.Projeto.controller.ObraController;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Artigo;
import maven.Projeto.model.Livro;
import maven.Projeto.model.Revista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;

public class CadastroObrasTela extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7860411679376119792L;
	private JComboBox<String> tipoCombo;
    private JTextField codField, tituloField, autorField, anoField, campoFiltro;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> sorter;
    private ObraController obraController = new ObraController();
    
    public CadastroObrasTela() {
    	setResizable(false);
        setTitle("Cadastro de Obras");
        setSize(740, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel painel = new JPanel(null) {
            /**
			 * 
			 */
			private static final long serialVersionUID = -2049954589829299812L;
			
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
        
        painel.add(criarLabel("Ano:", 510, 60));
        anoField = criarCampoTexto(550, 60);
        painel.add(anoField);
        
        JButton excluirBtn = new JButton("Excluir item");
        excluirBtn.setBounds(380, 150, 160, 30);
        painel.add(excluirBtn);
        
        JButton adicionarBtn = new JButton("Adicionar item");
        adicionarBtn.setBounds(200, 150, 160, 30);
        painel.add(adicionarBtn);
        
        painel.add(criarLabel("Filtrar:", 30, 200));
        campoFiltro = criarCampoTexto(90, 200);
        painel.add(campoFiltro);
        
        adicionarBtn.addActionListener(e -> {
            try {
                String tipo = (String) tipoCombo.getSelectedItem();
                String codigo = codField.getText();
                String tituloTxt = tituloField.getText();
                String autor = autorField.getText();
                String ano = anoField.getText();
                
                for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                    String codigoExistente = (String) modeloTabela.getValueAt(i, 0); 
                    if (codigoExistente.equals(codigo)) {
                        JOptionPane.showMessageDialog(this,
                            "Já existe um item com esse código.",
                            "Código Duplicado",
                            JOptionPane.WARNING_MESSAGE);
                        return; 
                    }
                }
                
                obraController.verificacaoDeDados(tipo, codigo, tituloTxt, autor, ano, false);
                atualizarTabela();
                modeloTabela.fireTableDataChanged();
                JOptionPane.showMessageDialog(this, "Item adicionado com sucesso!");
                
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        modeloTabela = new DefaultTableModel(new String[]{"Código", "Título", "Autor", "Ano", "Tipo"}, 0);
        tabela = new JTable(modeloTabela);
        sorter = new TableRowSorter<>(modeloTabela);
        tabela.setRowSorter(sorter);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 235, 670, 230);
        painel.add(scroll);
        
        excluirBtn.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um item para excluir.");
                return;
            }
            
            String codigo = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            String tipo = (String) modeloTabela.getValueAt(linhaSelecionada, 4);
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir esse item?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                try {
					obraController.exclusaoDeDados(tipo, codigo);
				} catch (CampoVazioException e1) {
					JOptionPane.showMessageDialog(this, "Código e Tipo inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
                
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Item excluído com sucesso!");
            }
        });
        
        campoFiltro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            
            private void filtrar() {
                String texto = campoFiltro.getText().toLowerCase();
                
                sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                    public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                        String titulo = entry.getStringValue(1).toLowerCase(); 
                        String autor = entry.getStringValue(2).toLowerCase();  
                        String tipo = entry.getStringValue(4).toLowerCase();   
                        return titulo.contains(texto) || autor.contains(texto) || tipo.contains(texto);
                    }
                });
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
        
        for (Livro livro : obraController.getLivros()) {
            modeloTabela.addRow(new Object[]{
                livro.getCodigo(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAnoDePublicacao(),
                "Livro"
            });
        }
        
        for (Revista revista : obraController.getRevistas()) {
            modeloTabela.addRow(new Object[]{
                revista.getCodigo(),
                revista.getTitulo(),
                revista.getAutor(),
                revista.getAnoDePublicacao(),
                "Revista"
            });
        }
        
        for (Artigo artigo : obraController.getArtigos()) {
            modeloTabela.addRow(new Object[]{
                artigo.getCodigo(),
                artigo.getTitulo(),
                artigo.getAutor(),
                artigo.getAnoDePublicacao(),
                "Artigo"
            });
        }
        modeloTabela.fireTableDataChanged();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroObrasTela().setVisible(true));
    }
}
