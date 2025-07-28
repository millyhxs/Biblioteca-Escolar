package maven.Projeto.view;

import javax.swing.*;

import maven.Projeto.controller.FuncionarioController;

import java.awt.*;

public class AdministradorTela extends JFrame {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3123822800837998866L;

	public AdministradorTela() {
        setTitle("Painel do Administrador");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setResizable(false);
        
        JPanel painel = new JPanel() {
            /**
			 * 
			 */
			private static final long serialVersionUID = -6812232095475454565L;

			protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(40, 40, 40));
            }
        };
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Área do Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 20, 400, 30);
        painel.add(titulo);
        
        Font fonteBtn = new Font("SansSerif", Font.BOLD, 16);
        Color corBotao = new Color(220, 53, 69);
        Color corTextoBotao = Color.WHITE;
        
        JButton btnObras = new JButton("Cadastrar Obras");
        btnObras.setBounds(100, 100, 200, 40);
        btnObras.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnObras.setBackground(corBotao);
        btnObras.setForeground(corTextoBotao);
        btnObras.setFocusPainted(false);
        painel.add(btnObras);
        btnObras.addActionListener(e -> new CadastroObrasTela().setVisible(true));
        
        JButton btnLeitores = new JButton("Cadastrar Leitores");
        btnLeitores.setBounds(100, 150, 200, 40);
        btnLeitores.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLeitores.setBackground(corBotao);
        btnLeitores.setForeground(corTextoBotao);
        btnLeitores.setFocusPainted(false);
        painel.add(btnLeitores);
        btnLeitores.addActionListener(e -> new CadastroLeitoresTela().setVisible(true));
        
        JButton btnFuncionarios = new JButton("Cadastrar Funcionários");
        btnFuncionarios.setBounds(100, 200, 200, 40);
        btnFuncionarios.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnFuncionarios.setBackground(corBotao);
        btnFuncionarios.setForeground(corTextoBotao);
        btnFuncionarios.setFocusPainted(false);
        painel.add(btnFuncionarios);
        btnFuncionarios.addActionListener(e -> new CadastroFuncionariosTela().setVisible(true));
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Janela foi fechada!");
            	FuncionarioController funcionarioController = new FuncionarioController();
                funcionarioController.logoOffFuncionario();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdministradorTela().setVisible(true);
        });
    }
}
