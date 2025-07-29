package maven.Projeto.view;

import javax.swing.*;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.util.ComponenteUtil;

import java.awt.*;

public class AdministradorTela extends JFrame {
	
	private static final long serialVersionUID = 3123822800837998866L;
	 private final ComponenteUtil util = new ComponenteUtil();
	 
	public AdministradorTela() {
		util.aplicarTemaPadrao(this, "Painel do Administrador", 400, 400);
        
        JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);
 
        
        JLabel titulo = new JLabel("Área do Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 20, 400, 50);
        painel.add(titulo);
        
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
            System.out.println("Usuario deslogado!");
            new FuncionarioController().logoOffFuncionario();
            new LoginTela().setVisible(true);
        });
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Janela foi fechada!");
                new FuncionarioController().logoOffFuncionario();
            }
        });
    }
}
