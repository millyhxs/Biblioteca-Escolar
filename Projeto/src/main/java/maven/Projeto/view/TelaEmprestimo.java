package maven.Projeto.view;

import maven.Projeto.controller.EmprestimoController;
import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.controller.LeitoresController;
import maven.Projeto.controller.ObraController;
import maven.Projeto.dao.EmprestimoDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.excepctions.MatriculaNaoEncontradaException;
import maven.Projeto.excepctions.ValorNuloException;
import maven.Projeto.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class TelaEmprestimo extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    
    public TelaEmprestimo() {
        setTitle("Tela de Empréstimo");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(40, 40, 40));
            }
        };
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Empréstimos de Obras", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 10, 800, 30);
        painel.add(titulo);
        
        modeloTabela = new DefaultTableModel(new String[]{"Código", "Título", "Autor", "Ano", "Tipo", "Status"}, 0);
        tabela = new JTable(modeloTabela) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                String status = (String) table.getValueAt(row, 5);
                switch (status) {
                    case "Disponível":
                        c.setBackground(new Color(0, 120, 0));
                        c.setForeground(Color.WHITE);
                        break;
                    case "Emprestado":
                        c.setBackground(new Color(204, 153, 0));
                        c.setForeground(Color.BLACK);
                        break;
                    case "Atrasado":
                        c.setBackground(new Color(180, 0, 0));
                        c.setForeground(Color.WHITE);
                        break;
                    default:
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                        break;
                }
                return c;
            }
        });
        	
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 60, 730, 330);
        painel.add(scroll);
        
        JButton emprestarBtn = new JButton("Realizar Empréstimo");
        emprestarBtn.setBounds(30, 410, 200, 30);
        painel.add(emprestarBtn);
        
        emprestarBtn.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma obra.");
                return;
            }
            
            String codigo = (String) tabela.getValueAt(linha, 0); 
            String status = (String) tabela.getValueAt(linha, 5);
            
            if (!status.equals("Disponível")) {
                JOptionPane.showMessageDialog(this, "A obra selecionada não está disponível para empréstimo.");
                return;
            }
            
            String matricula = JOptionPane.showInputDialog("Digite a matrícula do usuário:");
            try {
				LeitoresController.verificarMatriculaExistente(matricula);
			} catch (MatriculaNaoEncontradaException ex) {
				JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
				return;
			} catch (CampoVazioException ex) {				
				JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
			}
            
            try {
                Funcionario responsavel = FuncionarioController.BuscaFuncionarioAtivado();
            	String tipo = (String) tabela.getValueAt(linha, 4);
            	
            	int diasDeEmprestimo = 0;
            	switch (tipo) {
    			case "Livro":
    				diasDeEmprestimo = 7; 
    				break;
    			case "Artigo":
    				diasDeEmprestimo = 2;
    				break;
    			case "Revista":
    				diasDeEmprestimo = 3;
    				break;
    			default:
    				break;
    			}
				EmprestimoController.registrarEmprestimo(codigo, matricula, diasDeEmprestimo, responsavel.getNome());
                JOptionPane.showMessageDialog(this, "Empréstimo realizado com sucesso.");
                atualizarTabela();
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            } catch (ValorNuloException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });
        
        atualizarTabela();
    }
    
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Emprestimo> emprestimos = EmprestimoDAO.getEmprestimos();
        
        addObrasComStatus(ObraController.getLivros(), "Livro", emprestimos);
        addObrasComStatus(ObraController.getRevistas(), "Revista", emprestimos);
        addObrasComStatus(ObraController.getArtigos(), "Artigo", emprestimos);
        
        modeloTabela.fireTableDataChanged();
    }
    
    private void addObrasComStatus(List<? extends Obra> obras, String tipo, List<Emprestimo> emprestimos) {
        for (Obra obra : obras) {
            String status = "Disponível";
            
            for (Emprestimo emp : emprestimos) {
                if (emp.getCodigoObra().equals(obra.getCodigo())) {
                    if (LocalDate.now().isAfter(emp.getDataDevolucaoPrevista())) {
                        status = "Atrasado";
                    } else {
                        status = "Emprestado";
                    }
                    break;
                }
            }
            
            modeloTabela.addRow(new Object[]{
                obra.getCodigo(),
                obra.getTitulo(),
                obra.getAutor(),
                obra.getAnoDePublicacao(),
                tipo,
                status
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaEmprestimo().setVisible(true));
    }
}
