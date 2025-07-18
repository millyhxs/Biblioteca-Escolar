package maven.Projeto.view;

import javax.swing.*;

import maven.Projeto.controller.FuncionarioController;

import java.awt.*;

public class TelaBibliotecario extends JFrame {
	
    public TelaBibliotecario() {
        setTitle("Painel do Bibliotecário");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(40, 40, 40));
            }
        };
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Área do Bibliotecário", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 20, 400, 30);
        painel.add(titulo);
        
        Font fonteBtn = new Font("SansSerif", Font.BOLD, 16);
        Color corBotao = new Color(220, 53, 69);
        Color corTexto = Color.WHITE;
        int larguraBtn = 200;
        int xCentral = (400 - larguraBtn) / 2;
        
        JButton btnEmprestimos = new JButton("Empréstimos");
        btnEmprestimos.setBounds(xCentral, 100, larguraBtn, 40);
        btnEmprestimos.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEmprestimos.setBackground(corBotao);
        btnEmprestimos.setForeground(corTexto);
        btnEmprestimos.setFocusPainted(false);
        painel.add(btnEmprestimos);
        btnEmprestimos.addActionListener(e -> new TelaEmprestimo().setVisible(true));
        
        JButton btnDevolucoes = new JButton("Devoluções");
        btnDevolucoes.setBounds(xCentral, 150, larguraBtn, 40);
        btnDevolucoes.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnDevolucoes.setBackground(corBotao);
        btnDevolucoes.setForeground(corTexto);
        btnDevolucoes.setFocusPainted(false);
        painel.add(btnDevolucoes);
        btnDevolucoes.addActionListener(e -> new TelaDevolucao().setVisible(true));
        
        JButton btnRelatorios = new JButton("Relatórios");
        btnRelatorios.setBounds(xCentral, 200, larguraBtn, 40);
        btnRelatorios.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnRelatorios.setBackground(corBotao);
        btnRelatorios.setForeground(corTexto);
        btnRelatorios.setFocusPainted(false);
        painel.add(btnRelatorios);
        btnRelatorios.addActionListener(e -> new TelaRelatorios().setVisible(true));
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Janela foi fechada!");
                
                FuncionarioController.logoOffFuncionario();
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaBibliotecario().setVisible(true));
    }
}
