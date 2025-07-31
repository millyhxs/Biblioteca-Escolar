package maven.Projeto.view;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.exceptions.CampoVazioException;
import maven.Projeto.model.Funcionario;
import maven.Projeto.util.ComponenteUtil;
import maven.Projeto.util.FiltroTabelaUtil;

/**
 * Tela de cadastro, edição e exclusão de funcionários.
 * Permite adicionar novos funcionários, listar os existentes,
 * realizar buscas e aplicar filtros.
 * 
 * @author Millena
 */
public class CadastroFuncionariosTela extends JFrame {
	private static final long serialVersionUID = 1L;
	
	  // Instâncias
    private FuncionarioController funcionarioController = new FuncionarioController();
    private ComponenteUtil util = new ComponenteUtil();
    
	// Componentes da tela
	private JComboBox<String> nivelCombo;
    private JTextField idField, nomeField, campoFiltro;
    private JPasswordField senhaField;
    private JTable tabela;
    private DefaultTableModel modeloTabela;	
    private final Color corOriginalCampo = Color.WHITE;
    
    /**
     * Construtor que inicializa a interface de gerenciamento de funcionários.
     */
    public CadastroFuncionariosTela() {
    	util.aplicarTemaPadrao(this, "Cadastro de Funcionarios", 740, 560);
        
    	JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Cadastro de Funcionários", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 10, 700, 30);
        painel.add(titulo);
        
        // Campos de entrada
        painel.add(util.criarLabel("ID:", 30, 60));
        idField = util.criarCampoTexto(90, 60);
        painel.add(idField);
        
        painel.add(util.criarLabel("Nome:", 260, 60));
        nomeField = util.criarCampoTexto(330, 60);
        painel.add(nomeField);
        
        painel.add(util.criarLabel("Senha:", 500, 60));
        senhaField = new JPasswordField();
        senhaField.setBounds(550, 60, 150, 25);
        painel.add(senhaField);
        
        painel.add(util.criarLabel("Nível:", 260, 100));
        nivelCombo = new JComboBox<>(new String[]{"Administrador", "Bibliotecário", "Estagiário"});
        nivelCombo.setBounds(330, 100, 150, 25);
        painel.add(nivelCombo);
        
        // Botões de ação
        JButton adicionarBtn = util.criarBotao("Adicionar Funcionário", 60, 150, 190, 30, util.getCorBotaoSecundario());
        painel.add(adicionarBtn);
        getRootPane().setDefaultButton(adicionarBtn);
        
        JButton editarBtn = util.criarBotao("Editar Funcionário", 270, 150, 190, 30, util.getCorBotaoSecundario());
        painel.add(editarBtn);
        
        JButton excluirBtn = util.criarBotao("Excluir Funcionário", 480, 150, 190, 30, util.getCorBotaoSecundario());
        painel.add(excluirBtn);
        
        // Campo de filtro
        painel.add(util.criarLabel("Filtrar:", 30, 200));
        campoFiltro = util.criarCampoTexto(90, 200);
        painel.add(campoFiltro);
        
        // Tabela de dados
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Nível de Acesso"}, 0) {
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
        
        // Filtro dinâmico na tabela
        FiltroTabelaUtil filtro = new FiltroTabelaUtil(tabela);
        filtro.aplicarFiltroMultiplo(campoFiltro, new int[]{0, 1});
        
        atualizarTabela();
        
        // Ação do botão Adicionar
        adicionarBtn.addActionListener(e -> {
            String id = idField.getText();
            String nome = nomeField.getText();
            String senha = new String(senhaField.getPassword());
            String nivel = (String) nivelCombo.getSelectedItem();
            
            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                String codigoExistente = (String) modeloTabela.getValueAt(i, 0); 
                if (codigoExistente.equals(id)) {
                    JOptionPane.showMessageDialog(this,
                        "Já existe um Funcionario com esse ID.",
                        "ID Duplicado",
                        JOptionPane.WARNING_MESSAGE);
                    return; 
                }
            }
            
            try {
                funcionarioController.verificarCadastro(id, nome, senha, nivel);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
                limparCampos();
                sairModoEdicao();
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
            	JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
        });
        
        // Ação do botão Excluir
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
                    funcionarioController.excluirFuncionario(id);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                    limparCampos();
                    sairModoEdicao();
                } catch (CampoVazioException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });
        
        // Ação do botão Editar
        editarBtn.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário para editar.");
                return;
            }
            
            String idAntigo = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            String id = idField.getText();
            String nome = nomeField.getText();
            String senha = new String(senhaField.getPassword());
            String tipo = (String) nivelCombo.getSelectedItem();
            
            
            try {
                funcionarioController.editarFuncionario(idAntigo, id, nome, tipo, senha);
                atualizarTabela();
                limparCampos();
                editarBtn.setText("Editar Leitor");
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!"); 
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
                
                idField.setText((String) modeloTabela.getValueAt(linha, 0));
                nomeField.setText((String) modeloTabela.getValueAt(linha, 1));
                nivelCombo.setSelectedItem((String) modeloTabela.getValueAt(linha, 2));
                editarBtn.setText("Salvar Edição");
                entrarModoEdicao();
            }
        });
        
    }
    
    // Métodos de mudanças visuais pro usuário saber quando está no modo de edição.
    private void entrarModoEdicao() {
        idField.setBackground(util.getCorEdicao());
        nomeField.setBackground(util.getCorEdicao());
        senhaField.setBackground(util.getCorEdicao());
        nivelCombo.setBackground(util.getCorEdicao());
    }

    private void sairModoEdicao() {
        idField.setBackground(corOriginalCampo);
        nomeField.setBackground(corOriginalCampo);
        senhaField.setBackground(corOriginalCampo);
        nivelCombo.setBackground(corOriginalCampo);
    }

    
    /**
     * Limpa os campos de entrada.
     */
    private void limparCampos() {
    	idField.setText("");
    	nomeField.setText("");
    	senhaField.setText("");
    }
    
    /**
     * Atualiza os dados exibidos na tabela,
     * organizando por nível de acesso.
     */
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        
        List<String> ordem = Arrays.asList("Administrador", "Bibliotecário", "Estagiário");
        
        for (String nivel : ordem) {
            for (Funcionario f : funcionarioController.getFuncionarios()) {
                if (f.getTipo().equals(nivel)) {
                    modeloTabela.addRow(new Object[]{
                        f.getId(),
                        f.getNome(),
                        f.getTipo()
                    });
                }
            }
        }
    }
}
