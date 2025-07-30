package maven.Projeto.view;

import maven.Projeto.util.RelatoriosUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;

public class RelatorioTela extends JFrame {
	
	private static final long serialVersionUID = 2402584122525090331L;
	
	RelatoriosUtil relatoriosPDF = new RelatoriosUtil();
    
	public RelatorioTela() {
        setTitle("Relatórios Gerenciais");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painel = new JPanel() {
			private static final long serialVersionUID = 4106092340683141571L;

			protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(40, 40, 40));
            }
        };
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Gerar Relatórios", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 20, 400, 30);
        painel.add(titulo);
        
        int larguraBtn = 250;
        int alturaBtn = 40;
        int xCentral = (400 - larguraBtn) / 2;
        int yInicial = 80;
        int espacamento = 50;
        
        criarBotao(painel, "Empréstimos do mês", xCentral, yInicial, larguraBtn, alturaBtn, e ->  {
        	this.dispose();
        	new MesesRelatorioTela().setVisible(true);});
        criarBotao(painel, "Obras mais emprestadas", xCentral, yInicial + espacamento, larguraBtn, alturaBtn, e -> relatoriosPDF.gerarObrasMaisEmprestadas());
        criarBotao(painel, "Usuários com mais atrasos", xCentral, yInicial + espacamento * 2, larguraBtn, alturaBtn, e -> relatoriosPDF.gerarUsuariosComMaisAtrasos());
    }
    
    private void criarBotao(JPanel painel, String texto, int x, int y, int largura, int altura, Consumer<ActionEvent> acao) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, largura, altura);
        botao.setFont(new Font("SansSerif", Font.BOLD, 14));
        botao.setBackground(new Color(220, 53, 69));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.addActionListener(acao::accept);
        painel.add(botao);
    }
}
