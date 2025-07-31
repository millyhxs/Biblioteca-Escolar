package maven.Projeto.view;

import javax.swing.*;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.util.ComponenteUtil;

/**
 * Tela principal para acesso às funcionalidades do Administrador.
 * Permite o acesso as telas cadastrais
 * 
 * @author Millena
 */

public class AdministradorTela extends JFrame {
	private static final long serialVersionUID = 1L;
	
	// Utilitário para componentes visuais reutilizáveis
	ComponenteUtil util = new ComponenteUtil();

	/**
	 * Construtor que inicializa a interface do painel do Administrador.
	 */
	public AdministradorTela() {
		util.aplicarTemaPadrao(this, "Painel do Administrador", 400, 400);
        
        JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);
 
        
        JLabel titulo = new JLabel("Área do Administrador", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 20, 400, 50);
        painel.add(titulo);
        
        // Botões
        JButton btnCadastrarObras = util.criarBotao("Cadastrar Obras", 100, 100, 200, 40, util.getCorBotaoPrincipal());
        btnCadastrarObras.addActionListener(e -> new CadastroObrasTela().setVisible(true));
        painel.add(btnCadastrarObras);

        JButton btnCadastrarLeitores = util.criarBotao("Cadastrar Leitores", 100, 150, 200, 40, util.getCorBotaoPrincipal());
        btnCadastrarLeitores.addActionListener(e -> new CadastroLeitoresTela().setVisible(true));
        painel.add(btnCadastrarLeitores);

        JButton btnCadastrarFuncionarios = util.criarBotao("Cadastrar Funcionários", 100, 200, 200, 40, util.getCorBotaoPrincipal());
        btnCadastrarFuncionarios.addActionListener(e -> new CadastroFuncionariosTela().setVisible(true));
        painel.add(btnCadastrarFuncionarios);

        JButton btnSair = util.criarBotao("Sair", 100, 250, 200, 40, util.getCorBotaoSair());
        painel.add(btnSair);
        btnSair.addActionListener(e -> {
            dispose();
            new FuncionarioController().logoOffFuncionario();
            new LoginTela().setVisible(true);
        });
        
        // Encerra sessão do administrador ao fechar a janela
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Janela foi fechada!");
                new FuncionarioController().logoOffFuncionario();
            }
        });
    }
}
