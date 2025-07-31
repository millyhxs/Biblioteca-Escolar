package maven.Projeto.view;

import javax.swing.*;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.util.ComponenteUtil;

/**
 * Tela principal de acesso para o Bibliotecário.
 * Permite acesso às funcionalidades de empréstimos e relatórios.
 * 
 * @author Millena
 */

public class BibliotecarioTela extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Utilitário para componentes visuais reutilizáveis
	ComponenteUtil util = new ComponenteUtil();
	
	/**
	 * Construtor que monta a interface gráfica da área do Bibliotecário.
	 */
	public BibliotecarioTela() {
		util.aplicarTemaPadrao(this, "Painel do Bibliotecário", 400, 400);
        
		JPanel painel = util.painelComFundoNulo();
	    painel.setLayout(null);
	    getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Área do Bibliotecário", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 20, 400, 80);
        painel.add(titulo);
        
        // Botões
        JButton btnEmprestimos = util.criarBotao("Empréstimos", 100, 120, 200, 40, util.getCorBotaoPrincipal());
        btnEmprestimos.addActionListener(e -> new EmprestimoTela().setVisible(true));
        painel.add(btnEmprestimos);
        
        JButton btnRelatorios = util.criarBotao("Relatórios", 100, 170, 200, 40, util.getCorBotaoPrincipal());
        btnRelatorios.addActionListener(e -> {
        	
        	new RelatorioTela().setVisible(true);
        });
        painel.add(btnRelatorios);
        
        JButton btnSair = util.criarBotao("Sair", 100, 220, 200, 40, util.getCorBotaoSair());
        painel.add(btnSair);
        btnSair.addActionListener(e -> {
            dispose();
            new FuncionarioController().logoOffFuncionario();
            new LoginTela().setVisible(true);
        });
        
        // Encerra sessão do bibliotecário ao fechar a janela
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Janela foi fechada!");
                new FuncionarioController().logoOffFuncionario();
            }
        });
    }
}
