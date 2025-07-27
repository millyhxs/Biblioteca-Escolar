package maven.Projeto.view;

import maven.Projeto.controller.*;
import maven.Projeto.dao.DevolucaoDAO;
import maven.Projeto.dao.EmprestimoDAO;
import maven.Projeto.dao.MultaDAO;
import maven.Projeto.excepctions.*;
import maven.Projeto.model.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmprestimoTela extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JPanel painelPagamento;
    private JLabel valorMultaLabel;
    private JComboBox<String> metodoPagamentoBox;
    private JButton confirmarPagamentoBtn;
    private Emprestimo emprestimoSelecionado;
    int diasPermitidos;
    
    LocalDate data = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String dataFormatada = data.format(formatter);
    
    public EmprestimoTela() {
        setTitle("Tela de Empréstimo");
        setSize(900, 600);
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
        titulo.setBounds(0, 10, 900, 30);
        painel.add(titulo);
        
        modeloTabela = new DefaultTableModel(new String[]{"Código", "Título", "Autor", "Ano", "Tipo", "Status"}, 0){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                c.setForeground(Color.BLACK);
                
                if (column == 5 && value instanceof String) {
                    String status = ((String) value).toLowerCase();
                    switch (status) {
                        case "disponível":
                            c.setForeground(new Color(0, 160, 0)); // verde
                            break;
                        case "emprestado/a":
                            c.setForeground(new Color(220, 160, 0)); // amarelo
                            break;
                        case "em atraso":
                            c.setForeground(new Color(200, 0, 0)); // vermelho
                            break;
                    }
                }
                
                c.setBackground(isSelected ? tabela.getSelectionBackground() : Color.WHITE);
                
                return c;
            }
        });
        	
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 60, 830, 300);
        painel.add(scroll);
        
        JButton emprestarBtn = new JButton("Realizar Empréstimo");
        emprestarBtn.setBounds(30, 380, 200, 30);
        painel.add(emprestarBtn);
        
        JButton devolverBtn = new JButton("Registrar Devolução");
        devolverBtn.setBounds(250, 380, 200, 30);
        painel.add(devolverBtn);
        
        painelPagamento = new JPanel(null);
        painelPagamento.setBounds(480, 370, 380, 150);
        painelPagamento.setBorder(BorderFactory.createTitledBorder("Pagamento de Multa"));
        painelPagamento.setBackground(new Color(60, 60, 60));
        
        JLabel metodoLabel = new JLabel("Método:");
        metodoLabel.setForeground(Color.WHITE);
        metodoLabel.setBounds(20, 30, 100, 25);
        painelPagamento.add(metodoLabel);
        
        metodoPagamentoBox = new JComboBox<>(new String[]{"PIX", "Dinheiro", "Cartão"});
        metodoPagamentoBox.setBounds(100, 30, 150, 25);
        painelPagamento.add(metodoPagamentoBox);
        
        valorMultaLabel = new JLabel("Multa: R$ 0.00");
        valorMultaLabel.setForeground(Color.WHITE);
        valorMultaLabel.setBounds(20, 60, 200, 25);
        painelPagamento.add(valorMultaLabel);
        
        confirmarPagamentoBtn = new JButton("Confirmar Pagamento");
        confirmarPagamentoBtn.setBounds(100, 100, 180, 30);
        painelPagamento.add(confirmarPagamentoBtn);
        painelPagamento.setVisible(false);
        
        painel.add(painelPagamento);
        
        emprestarBtn.addActionListener(e -> emprestar());
        devolverBtn.addActionListener(e -> prepararDevolucao());
        confirmarPagamentoBtn.addActionListener(e -> registrarPagamento());
        
        atualizarTabela();
    }
    
    private void emprestar() {
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
		} catch (MatriculaNaoEncontradaException | CampoVazioException ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            return;
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
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }
	
	private void prepararDevolucao() {
		int linha = tabela.getSelectedRow();
		if (linha == -1) {
		    JOptionPane.showMessageDialog(this, "Selecione uma obra.");
		    return;
		}
		
		String status = (String) tabela.getValueAt(linha, 5);
		String codigo = (String) tabela.getValueAt(linha, 0);
		
		emprestimoSelecionado = EmprestimoController.getEmprestimos().stream()
		        .filter(e -> e.getCodigoObra().equals(codigo))
		        .findFirst().orElse(null);
		
		if (emprestimoSelecionado == null) {
		    JOptionPane.showMessageDialog(this, "Esta obra já está disponível.");
		    return;
		}
		
		if (status.trim().equalsIgnoreCase("Em atraso")) {
			linha = tabela.getSelectedRow();
	        String tipo = (String) tabela.getValueAt(linha, 4);
	        
	        if (tipo.equals("Livro") ) {
	        	Livro livro = new Livro();
	        	diasPermitidos = livro.getTempoEmprestimo();
	        }
	        else if (tipo.equals("Artigo") ) {
	        	Artigo artigo = new Artigo();
	        	diasPermitidos = artigo.getTempoEmprestimo();
	        } 
	        else if (tipo.equals("Revista") ) {
	        	Revista revista = new Revista();
	        	diasPermitidos = revista.getTempoEmprestimo();	
	        }
	        
	        float valorMulta = EmprestimoDAO.verificarMultaParaEmprestimo(emprestimoSelecionado, diasPermitidos);
	        
	        if (valorMulta > 0f) {
	        	valorMultaLabel.setText("Multa: R$ " + String.format("%.2f", valorMulta));
	        	painelPagamento.setVisible(true);
	        } else {
	        	EmprestimoController.devolverObra(codigo);
	        	atualizarTabela();
	        }
		    
		} else {
		    painelPagamento.setVisible(false);
		    JOptionPane.showMessageDialog(this, "Devolução concluída.");
		    
		    String dataFormatada = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		    Devolucao devolucao = new Devolucao(
		            emprestimoSelecionado.getCodigoObra(),
		            emprestimoSelecionado.getMatriculaUsuario(),
		            dataFormatada
		    );
		    
		    DevolucaoDAO.registrarDevolucao(devolucao);
		    EmprestimoController.devolverObra(codigo);
		    atualizarTabela();
		}
    }
	
    private void registrarPagamento() {
        if (emprestimoSelecionado == null) { 
        	return;
        }

        int linha = tabela.getSelectedRow();
        String tipo = (String) tabela.getValueAt(linha, 4);
        
        if (tipo.equals("Livro") ) {
        	Livro livro = new Livro();
        	diasPermitidos = livro.getTempoEmprestimo();
        }
        else if (tipo.equals("Artigo") ) {
        	Artigo artigo = new Artigo();
        	diasPermitidos = artigo.getTempoEmprestimo();
        } 
        else if (tipo.equals("Revista") ) {
        	Revista revista = new Revista();
        	diasPermitidos = revista.getTempoEmprestimo();	
        }
        float valorMulta = EmprestimoDAO.verificarMultaParaEmprestimo(emprestimoSelecionado, diasPermitidos);
        PagamentoMulta pagamento = new PagamentoMulta(
        	    emprestimoSelecionado.getMatriculaUsuario(),
        	    valorMulta,
        	    LocalDate.now(),
        	    metodoPagamentoBox.getSelectedItem().toString()
        	);
        MultaDAO.registrarPagamento(pagamento);
        
        Devolucao devolucao = new Devolucao( emprestimoSelecionado.getCodigoObra(), emprestimoSelecionado.getMatriculaUsuario(), dataFormatada);
        DevolucaoDAO.registrarDevolucao(devolucao);
        EmprestimoController.devolverObra(emprestimoSelecionado.getCodigoObra());
        
        painelPagamento.setVisible(false);
        JOptionPane.showMessageDialog(this, "Pagamento registrado e devolução concluída.");
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
                        status = "Em atraso";
                    } else {
                        status = "Emprestado/a";
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
        SwingUtilities.invokeLater(() -> new EmprestimoTela().setVisible(true));
    }
}
