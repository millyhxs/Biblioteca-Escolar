package maven.Projeto.view;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Funcionario;
import maven.Projeto.util.ComponenteUtil;
import maven.Projeto.util.FiltroTabelaUtil;

public class CadastroFuncionariosTela extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> nivelCombo;
    private JTextField idField, nomeField, campoFiltro;
    private JPasswordField senhaField;
    private JTable tabela;
    private DefaultTableModel modeloTabela;	
    private TableRowSorter<DefaultTableModel> sorter;
    private FuncionarioController funcionarioController = new FuncionarioController();
    private ComponenteUtil util = new ComponenteUtil();
    
    
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
        
        JButton adicionarBtn = util.criarBotao("Adicionar Funcionário", 110, 150, 160, 30, util.getCorBotaoSecundario());
        painel.add(adicionarBtn);
        
        JButton editarBtn = util.criarBotao("Editar Funcionário", 290, 150, 160, 30, util.getCorBotaoSecundario());
        painel.add(editarBtn);
        
        JButton excluirBtn = util.criarBotao("Excluir Funcionário", 470, 150, 160, 30, util.getCorBotaoSecundario());
        painel.add(excluirBtn);
        
        painel.add(util.criarLabel("Filtrar:", 30, 200));
        campoFiltro = util.criarCampoTexto(90, 200);
        painel.add(campoFiltro);
        
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
        
        FiltroTabelaUtil filtro = new FiltroTabelaUtil(tabela);
        filtro.aplicarFiltroMultiplo(campoFiltro, new int[]{0, 1});
        
        atualizarTabela();
        
        adicionarBtn.addActionListener(e -> {
            String id = idField.getText();
            String nome = nomeField.getText();
            String senha = new String(senhaField.getPassword());
            String nivel = (String) nivelCombo.getSelectedItem();
            
            try {
                funcionarioController.verificarCadastro(id, nome, senha, nivel);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
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
                    funcionarioController.excluirFuncionario(id);
                    atualizarTabela();
                    JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                } catch (CampoVazioException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
            
        });
        
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
            	atualizarTabela();
                funcionarioController.editarFuncionario(idAntigo, id, nome, tipo, senha);
                atualizarTabela();
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        atualizarTabela();
    }
    
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
