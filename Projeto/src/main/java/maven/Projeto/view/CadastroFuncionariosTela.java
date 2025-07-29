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

@SuppressWarnings("serial")
public class CadastroFuncionariosTela extends JFrame {
    private JComboBox<String> nivelCombo;
    private JTextField idField, nomeField, campoFiltro;
    private JPasswordField senhaField;
    private JTable tabela;
    private DefaultTableModel modeloTabela;	
    private TableRowSorter<DefaultTableModel> sorter;
    private FuncionarioController funcionarioController = new FuncionarioController();
    
    
    public CadastroFuncionariosTela() {
        setResizable(false);
        setTitle("Cadastro de Funcionários");
        setSize(740, 560);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel painel = new JPanel(null) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1439219301304304992L;

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
        idField = criarCampoTexto(90, 60);
        painel.add(idField);
        
        painel.add(criarLabel("Nome:", 260, 60));
        nomeField = criarCampoTexto(330, 60);
        painel.add(nomeField);
        
        painel.add(criarLabel("Senha:", 500, 60));
        senhaField = new JPasswordField();
        senhaField.setBounds(550, 60, 150, 25);
        painel.add(senhaField);
        
        painel.add(criarLabel("Nível:", 260, 100));
        nivelCombo = new JComboBox<>(new String[]{"Administrador", "Bibliotecário", "Estagiário"});
        nivelCombo.setBounds(330, 100, 150, 25);
        painel.add(nivelCombo);
        
        JButton adicionarBtn = new JButton("Adicionar Funcionário");
        adicionarBtn.setBounds(110, 150, 160, 30);
        painel.add(adicionarBtn);
        
        JButton excluirBtn = new JButton("Excluir Funcionário");
        excluirBtn.setBounds(470, 150, 160, 30);
        painel.add(excluirBtn);
        
        JButton editarBtn = new JButton("Editar Funcionário");
        editarBtn.setBounds(290, 150, 160, 30);
        painel.add(editarBtn);
        
        painel.add(criarLabel("Filtrar:", 30, 200));
        campoFiltro = criarCampoTexto(90, 200);
        painel.add(campoFiltro);
        
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Nível de Acesso"}, 0) {
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CadastroFuncionariosTela().setVisible(true));
    }
}
