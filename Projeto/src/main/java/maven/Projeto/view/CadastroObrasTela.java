package maven.Projeto.view;

import maven.Projeto.controller.ObraController;
import maven.Projeto.exceptions.CampoVazioException;
import maven.Projeto.model.Artigo;
import maven.Projeto.model.Livro;
import maven.Projeto.model.Revista;
import maven.Projeto.util.ComponenteUtil;
import maven.Projeto.util.FiltroTabelaUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class CadastroObrasTela extends JFrame {
	private static final long serialVersionUID = -7860411679376119792L;
	
	private JComboBox<String> tipoCombo;
    private JTextField tituloField, autorField, anoField, campoFiltro;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private TableRowSorter<DefaultTableModel> sorter;
    private ObraController obraController = new ObraController();
    private ComponenteUtil util = new ComponenteUtil();
    
    public CadastroObrasTela() {
    	util.aplicarTemaPadrao(this, "Cadastro de Obras", 740, 560);
        
        JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Cadastro de Obras", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 10, 700, 30);
        painel.add(titulo);
        
        painel.add(util.criarLabel("Tipo:", 30, 60));
        tipoCombo = new JComboBox<>(new String[]{"Livro", "Revista", "Artigo"});
        tipoCombo.setBounds(100, 60, 150, 25);
        painel.add(tipoCombo);
        
        painel.add(util.criarLabel("Título:", 270, 60));
        tituloField = util.criarCampoTexto(340, 60);
        painel.add(tituloField);
        
        painel.add(util.criarLabel("Autor:", 270, 100));
        autorField = util.criarCampoTexto(340, 100);
        painel.add(autorField);
        
        painel.add(util.criarLabel("Ano:", 510, 60));
        anoField = util.criarCampoTexto(550, 60);
        painel.add(anoField);
        
        anoField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                // impede digitar se já tiver 4 caracteres ou não for número
                if (!Character.isDigit(c) || anoField.getText().length() >= 4) {
                    e.consume();
                }
            }
        });

        
        JButton adicionarBtn = util.criarBotao("Adicionar item", 200, 150, 160, 30, util.getCorBotaoSecundario());
        painel.add(adicionarBtn);
        
        JButton excluirBtn = util.criarBotao("Excluir item", 380, 150, 160, 30, util.getCorBotaoSecundario());
        painel.add(excluirBtn);
        
        painel.add(util.criarLabel("Filtrar:", 30, 200));
        campoFiltro = util.criarCampoTexto(90, 200);
        painel.add(campoFiltro);
        
        modeloTabela = new DefaultTableModel(new String[]{"Código", "Título", "Autor", "Ano", "Tipo"}, 0){
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
			
			
        };
        tabela = new JTable(modeloTabela);
        tabela.setRowSelectionAllowed(true);
        tabela.setAutoCreateRowSorter(true);
        tabela.getTableHeader().setReorderingAllowed(false);
     
        
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 235, 670, 260);
        painel.add(scroll);
        
        FiltroTabelaUtil filtro = new FiltroTabelaUtil(tabela);
        filtro.aplicarFiltroMultiplo(campoFiltro, new int[]{0, 1, 2});
        
        atualizarTabela();
        
        
        adicionarBtn.addActionListener(e -> {
            try {
                String tipo = (String) tipoCombo.getSelectedItem();
                String codigo = obraController.gerarCodigo(tipo);
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
                
                limparCampos();
                
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
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
        
    }
    
    private void limparCampos() {
        tituloField.setText("");
        autorField.setText("");
        anoField.setText("");
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
}
