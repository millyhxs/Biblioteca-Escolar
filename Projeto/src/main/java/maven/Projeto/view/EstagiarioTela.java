package maven.Projeto.view;

import java.awt.*;
import javax.swing.*;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.util.ComponenteUtil;

class EstagiarioTela extends JFrame {

    private static final long serialVersionUID = 1143581939582089287L;
    private final ComponenteUtil util = new ComponenteUtil();

    public EstagiarioTela() {
        util.aplicarTemaPadrao(this, "Painel do Estagiário", 400, 400);

        JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);

        JLabel titulo = new JLabel("Área do Estagiário", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 20, 400, 120);
        painel.add(titulo);

        JButton btnDevolucao = util.criarBotao("Registrar Devolução", 100, 150, 200, 40, util.getCorBotaoPrincipal());
        btnDevolucao.addActionListener(e -> new DevolucaoTela().setVisible(true));
        painel.add(btnDevolucao);
       

        JButton btnSair = util.criarBotao("Sair", 100, 200, 200, 40, util.getCorBotaoSair());
        painel.add(btnSair);
        btnSair.addActionListener(e -> {
            dispose();
            new FuncionarioController().logoOffFuncionario();
            new LoginTela().setVisible(true);
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                new FuncionarioController().logoOffFuncionario();
            }
        });
    }
}
