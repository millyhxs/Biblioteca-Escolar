package maven.Projeto.view;

import javax.swing.*;
import java.awt.*;

public class TelaAdministrador extends JFrame {

    public TelaAdministrador() {
        setTitle("Painel do Administrador");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        
        JPanel painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(45, 45, 45));
            }
        };
        painel.setLayout(null);
        add(painel);

        JLabel titulo = new JLabel("Área do Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 30, 500, 40);
        painel.add(titulo);

        Font fonteBtn = new Font("SansSerif", Font.BOLD, 16);
        Color corBotao = new Color(220, 53, 69);
        Color corTextoBotao = Color.WHITE;

        JButton btnObras = new JButton("Cadastrar Obras");
        btnObras.setBounds(150, 100, 200, 40);
        btnObras.setFont(fonteBtn);
        btnObras.setBackground(corBotao);
        btnObras.setForeground(corTextoBotao);
        btnObras.setFocusPainted(false);
        painel.add(btnObras);

        JButton btnLeitores = new JButton("Cadastrar Leitores");
        btnLeitores.setBounds(150, 160, 200, 40);
        btnLeitores.setFont(fonteBtn);
        btnLeitores.setBackground(corBotao);
        btnLeitores.setForeground(corTextoBotao);
        btnLeitores.setFocusPainted(false);
        painel.add(btnLeitores);

        JButton btnFuncionarios = new JButton("Cadastrar Funcionários");
        btnFuncionarios.setBounds(150, 220, 200, 40);
        btnFuncionarios.setFont(fonteBtn);
        btnFuncionarios.setBackground(corBotao);
        btnFuncionarios.setForeground(corTextoBotao);
        btnFuncionarios.setFocusPainted(false);
        painel.add(btnFuncionarios);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaAdministrador().setVisible(true);
        });
    }
}
