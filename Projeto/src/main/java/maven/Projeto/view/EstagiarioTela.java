package maven.Projeto.view;

import javax.swing.*;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.util.ComponenteUtil;

/**
 * Tela principal de acesso para o Estágiario.
 * Encaminha para a área de devolução.
 * 
 * @author Millena
 */
class EstagiarioTela extends JFrame {
    private static final long serialVersionUID = 1L;
    
    // Utilitário para componentes visuais reutilizáveis
    ComponenteUtil util = new ComponenteUtil();

    /**
	 * Construtor que monta a interface gráfica da área do Estágiario.
	 */
    public EstagiarioTela() {
        util.aplicarTemaPadrao(this, "Painel do Estagiário", 400, 400);

        JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);

        JLabel titulo = new JLabel("Área do Estagiário", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 20, 400, 120);
        painel.add(titulo);

        // Botões
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
        
        // Encerra sessão do estágiario ao fechar a janela
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                new FuncionarioController().logoOffFuncionario();
            }
        });
    }
}
