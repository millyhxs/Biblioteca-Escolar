package maven.Projeto.view;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import javax.swing.table.*;

import maven.Projeto.controller.LeitoresController;
import maven.Projeto.exceptions.CampoVazioException;
import maven.Projeto.model.Leitor;
import maven.Projeto.util.ComponenteUtil;
import maven.Projeto.util.FiltroTabelaUtil;

/**
 * Tela de cadastro, edição e exclusão de leitores.
 * Permite também listar os leitores já existentes,
 * e realizar buscas.
 * 
 * @author Millena
 */
public class CadastroLeitoresTela extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Componentes da tela
	private JComboBox<String> tipoCombo;
    private JTextField matriculaField, nomeField, telefoneField, emailField, campoFiltro;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private final Color corOriginalCampo = Color.WHITE;
    
    // Instâncias de utilitários e controlador
    private LeitoresController leitoresController = new LeitoresController();
    private ComponenteUtil util = new ComponenteUtil();
    
    /**
     * Construtor que inicializa a interface de gerenciamento de leitores.
     */
	public CadastroLeitoresTela() {
		util.aplicarTemaPadrao(this, "Cadastro de Leitores", 740, 560);
		
		JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Cadastro de Leitores", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 10, 700, 30);
        painel.add(titulo);
        
        // Campos de entrada
        painel.add(util.criarLabel("Tipo:", 30, 100));
        tipoCombo = new JComboBox<>(new String[]{"Aluno", "Professor", "Servidor"});
        tipoCombo.setBounds(100, 100, 150, 25);
        painel.add(tipoCombo);
        
        painel.add(util.criarLabel("Matricula:", 270, 60));
        matriculaField = util.criarCampoTexto(340, 60);
        painel.add(matriculaField);
        
        painel.add(util.criarLabel("Nome:", 30, 60));
        nomeField = util.criarCampoTexto(100, 60);
        painel.add(nomeField);
        
        painel.add(util.criarLabel("Telefone:", 270, 100));
        
        // Campo de telefone com máscara
        try {
            MaskFormatter maskTelefone = new MaskFormatter("(##) #####-####");
            maskTelefone.setPlaceholderCharacter('_');
            telefoneField = new JFormattedTextField(maskTelefone);
            telefoneField.setBounds(340, 100, 150, 25);
            painel.add(telefoneField);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
        painel.add(util.criarLabel("Email:", 510, 60));
        emailField = util.criarCampoTexto(550, 60);
        painel.add(emailField);
        
        // Botões de ação
        JButton adicionarBtn = util.criarBotao("Adicionar Leitor", 110, 150, 160, 30, util.getCorBotaoSecundario());
        painel.add(adicionarBtn);
        getRootPane().setDefaultButton(adicionarBtn);

        JButton editarBtn = util.criarBotao("Editar Leitor", 290, 150, 160, 30, util.getCorBotaoSecundario());
        painel.add(editarBtn);
        
        JButton excluirBtn = util.criarBotao("Excluir Leitor", 470, 150, 160, 30, util.getCorBotaoSecundario());
        painel.add(excluirBtn);
 
        // Campo de filtro
        painel.add(util.criarLabel("Filtrar:", 30, 200));
        campoFiltro = util.criarCampoTexto(90, 200);
        painel.add(campoFiltro);
        
        // Tabela de dados
        modeloTabela = new DefaultTableModel(new String[]{"Matrícula", "Nome", "Telefone", "Email", "Tipo"}, 0) {
			private static final long serialVersionUID = -6839186715585930198L;

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
        scroll.setBounds(30, 235, 670, 260);
        painel.add(scroll);
        
        // Filtro dinâmico na tabela
        FiltroTabelaUtil filtro = new FiltroTabelaUtil(tabela);
        filtro.aplicarFiltroMultiplo(campoFiltro, new int[]{0, 1, 2, 3});
        
        atualizarTabela();
        
        // Ação do botão Adicionar
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
                sairModoEdicao();
                limparCampos();
                
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Ação do botão Excluir
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
                limparCampos();
                sairModoEdicao();
            }
        });
        
        // Ação do botão Editar
        editarBtn.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um leitor para editar.");
                return;
            }
            
            String matriculaAntiga = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            String nome = nomeField.getText();
            String telefone = telefoneField.getText();
            String email = emailField.getText();
            String tipo = (String) tipoCombo.getSelectedItem();
            
            try {
                leitoresController.editarUsuario(matriculaAntiga, matriculaAntiga, nome, tipo, telefone, email);
                atualizarTabela();
                limparCampos();
                editarBtn.setText("Editar Leitor");
                matriculaField.setEditable(true);
                JOptionPane.showMessageDialog(this, "Leitor atualizado com sucesso!");
                limparCampos();
                sairModoEdicao();
                
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        
        });
        
        // Preenche campos ao selecionar uma linha
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                int linha = tabela.getSelectedRow();
                matriculaField.setText((String) modeloTabela.getValueAt(linha, 0));
                nomeField.setText((String) modeloTabela.getValueAt(linha, 1));
                telefoneField.setText((String) modeloTabela.getValueAt(linha, 2));
                emailField.setText((String) modeloTabela.getValueAt(linha, 3));
                tipoCombo.setSelectedItem((String) modeloTabela.getValueAt(linha, 4));

                matriculaField.setEditable(false);
                editarBtn.setText("Salvar Edição");
                entrarModoEdicao();
            }
        });


	}
	
	 // Métodos de mudanças visuais pro usuário saber quando está no modo de edição.
    private void entrarModoEdicao() {
        emailField.setBackground(util.getCorEdicao());
        nomeField.setBackground(util.getCorEdicao());
        matriculaField.setBackground(util.getCorEdicao());
        telefoneField.setBackground(util.getCorEdicao());
        tipoCombo.setBackground(util.getCorEdicao());
    }

    private void sairModoEdicao() {
        emailField.setBackground(corOriginalCampo);
        nomeField.setBackground(corOriginalCampo);
        matriculaField.setBackground(corOriginalCampo);
        telefoneField.setBackground(corOriginalCampo);
        tipoCombo.setBackground(corOriginalCampo);
    }
	
	/**
     * Limpa os campos de entrada.
     */
	private void limparCampos() {
		matriculaField.setText("");
	    nomeField.setText("");
	    telefoneField.setText("");
	    emailField.setText("");
	    tipoCombo.setSelectedIndex(0);
	    matriculaField.setEditable(true);
	}

	
	/**
     * Atualiza os dados exibidos na tabela,
     * organizando por tipo de usuário (Aluno/Professor/Servidor) .
     */
	private void atualizarTabela() {
	    modeloTabela.setRowCount(0);

	    List<String> ordem = Arrays.asList("Aluno", "Professor", "Servidor");

	    for (String tipo : ordem) {
	        for (Leitor leitor : leitoresController.getLeitores()) {
	            if (leitor.getTipoDeUsuario().equals(tipo)) {
	                modeloTabela.addRow(new Object[]{
	                    leitor.getMatricula(),
	                    leitor.getNome(),
	                    leitor.getTelefone(),
	                    leitor.getEmail(),
	                    leitor.getTipoDeUsuario()
	                });
	            }
	        }
	    }
	}
}
