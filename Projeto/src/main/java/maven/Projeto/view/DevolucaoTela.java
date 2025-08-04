package maven.Projeto.view;

import maven.Projeto.controller.EmprestimoController;
import maven.Projeto.controller.MultaDevolucaoController;
import maven.Projeto.controller.ObraController;
import maven.Projeto.model.*;
import maven.Projeto.util.ComponenteUtil;
import maven.Projeto.util.FiltroTabelaUtil;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class DevolucaoTela extends JFrame {
	private static final long serialVersionUID = 1143581939582089287L;

	 
	private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JPanel painelPagamento;
    private JLabel valorMultaLabel;
    private JComboBox<String> metodoPagamentoBox;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton confirmarPagamentoBtn;
    private JTextField campoFiltro;
    
    private Emprestimo emprestimoSelecionado;
    private ObraController obraController = new ObraController();
    private EmprestimoController emprestimoController = new EmprestimoController();
    private MultaDevolucaoController multaDevolucaoController = new MultaDevolucaoController();
    private final ComponenteUtil util = new ComponenteUtil();
    
    private int diasPermitidos;
    
    public DevolucaoTela() {
    	util.aplicarTemaPadrao(this, "Tela de Devolução", 900, 600);
        JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Registrar Devoluções", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 10, 900, 30);
        painel.add(titulo);
        
        modeloTabela = new DefaultTableModel(new String[]{"Código", "Título", "Autor", "Ano", "Tipo", "Status"}, 0) {
			private static final long serialVersionUID = -6426250182220130365L;
			
			public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.getTableHeader().setReorderingAllowed(false);
        sorter = new TableRowSorter<>(modeloTabela);
        tabela.setRowSorter(sorter);
        tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -2787758373329218192L;
			
			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.BLACK);
                
                if (column == 5 && value instanceof String) {
                    String status = ((String) value).toLowerCase();
                    switch (status) {
                        case "disponível":
                            c.setForeground(new Color(0, 160, 0));
                            break;
                        case "emprestado/a":
                            c.setForeground(new Color(220, 160, 0));
                            break;
                        case "em atraso":
                            c.setForeground(new Color(200, 0, 0));
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
        
        campoFiltro = util.criarCampoTexto(30, 380);
        campoFiltro.setSize(410, 30);
        painel.add(campoFiltro);
        FiltroTabelaUtil filtro = new FiltroTabelaUtil(tabela);
        filtro.aplicarFiltroMultiplo(campoFiltro, new int[]{1, 2, 4});
        
        JButton devolverBtn = util.criarBotao("Registrar Devolução", 30, 420, 200, 30, util.getCorBotaoSecundario());
        painel.add(devolverBtn);
                
        painelPagamento = new JPanel(null);
        painelPagamento.setBounds(480, 380, 380, 150);
        painelPagamento.setBorder(BorderFactory.createTitledBorder("Pagamento de Multa"));
        painelPagamento.setBackground(new Color(60, 60, 60));
        
        JLabel metodoLabel = util.criarLabel("Método:", 20, 30);
        painelPagamento.add(metodoLabel);
        
        metodoPagamentoBox = new JComboBox<>(new String[]{"PIX", "Dinheiro", "Cartão"});
        metodoPagamentoBox.setBounds(100, 30, 150, 25);
        painelPagamento.add(metodoPagamentoBox);
        
        valorMultaLabel = util.criarLabel("Multa: R$ 0.00", 20, 60);
        painelPagamento.add(valorMultaLabel);
        
        confirmarPagamentoBtn = util.criarBotao("Confirmar Pagamento", 100, 100, 180, 30, util.getCorBotaoSecundario());
        painelPagamento.add(confirmarPagamentoBtn);
        painelPagamento.setVisible(false);
        
        painel.add(painelPagamento);
        
        devolverBtn.addActionListener(e -> prepararDevolucao());
        confirmarPagamentoBtn.addActionListener(e -> registrarPagamento());
        
        atualizarTabela();
    } 
    
    private void prepararDevolucao() {
    	int linha = tabela.getSelectedRow();
		if (linha == -1) {
		    JOptionPane.showMessageDialog(this, "Selecione uma obra.");
		    return;
		}
		
		String status = (String) tabela.getValueAt(linha, 5);
		String codigo = (String) tabela.getValueAt(linha, 0);
		
		emprestimoSelecionado = emprestimoController.getEmprestimos().stream()
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
	        float valorMulta = emprestimoController.verificarMulta(emprestimoSelecionado, diasPermitidos);
	        
	        if (valorMulta > 0f) {
	        	valorMultaLabel.setText("Multa: R$ " + String.format("%.2f", valorMulta));
	        	painelPagamento.setVisible(true);
	        } else {
	        	emprestimoController.devolverObra(codigo);
	        	atualizarTabela();
	        }
		    
		} else {
		    painelPagamento.setVisible(false);
		    JOptionPane.showMessageDialog(this, "Devolução concluída.");
		    
		    emprestimoController.devolverObra(codigo);
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
        float valorMulta = emprestimoController.verificarMulta(emprestimoSelecionado, diasPermitidos);
        PagamentoMulta pagamento = new PagamentoMulta(
        	    emprestimoSelecionado.getMatriculaUsuario(),
        	    valorMulta,
        	    LocalDate.now(),
        	    metodoPagamentoBox.getSelectedItem().toString()
        	);
		multaDevolucaoController.registroDePagamento(pagamento);
        
        emprestimoController.devolverObra(emprestimoSelecionado.getCodigoObra());
        
        painelPagamento.setVisible(false);
        JOptionPane.showMessageDialog(this, "Pagamento registrado e devolução concluída.");
        atualizarTabela();
    }
    
    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Emprestimo> emprestimos = emprestimoController.getEmprestimos();
        
        addObrasComStatus(obraController.getLivros(), "Livro", emprestimos);
        addObrasComStatus(obraController.getRevistas(), "Revista", emprestimos);
        addObrasComStatus(obraController.getArtigos(), "Artigo", emprestimos);
        
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
        
        campoFiltro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            
            private void filtrar() {
                String texto = campoFiltro.getText().toLowerCase();
                if (texto.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                        public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                            String titulo = entry.getStringValue(1).toLowerCase();
                            String autor = entry.getStringValue(2).toLowerCase();
                            String tipo = entry.getStringValue(4).toLowerCase();
                            return titulo.contains(texto) || autor.contains(texto) || tipo.contains(texto);
                        }
                    });
                }
            }
        });
        
    }
}
