package maven.Projeto.view;

import maven.Projeto.util.ComponenteUtil;
import maven.Projeto.util.RelatoriosUtil;

import javax.swing.*;

public class RelatorioTela extends JFrame {
	
	private static final long serialVersionUID = 1L;
	ComponenteUtil util = new ComponenteUtil();
	RelatoriosUtil relatoriosPDF = new RelatoriosUtil();
    
	public RelatorioTela() {
		util.aplicarTemaPadrao(this, "Relatórios Gerenciais", 400, 400);
        
		JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Gerar Relatórios", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 20, 400, 50);
        painel.add(titulo);
       
        JButton btnEmprestimosMes = util.criarBotao("Empréstimos do mês", 75, 100, 250, 40, util.getCorBotaoPrincipal());
        btnEmprestimosMes.addActionListener(e -> {
            dispose();
            new MesesRelatorioTela().setVisible(true);
        });
        painel.add(btnEmprestimosMes);

        JButton btnObrasMaisEmprestadas = util.criarBotao("Obras mais emprestadas", 75, 150, 250, 40, util.getCorBotaoPrincipal());
        btnObrasMaisEmprestadas.addActionListener(e -> relatoriosPDF.gerarObrasMaisEmprestadas());
        painel.add(btnObrasMaisEmprestadas);

        JButton btnUsuariosComMaisAtrasos = util.criarBotao("Usuários com mais atrasos", 75, 200, 250, 40, util.getCorBotaoPrincipal());
        btnUsuariosComMaisAtrasos.addActionListener(e -> relatoriosPDF.gerarUsuariosComMaisAtrasos());
        painel.add(btnUsuariosComMaisAtrasos);

        JButton btnVoltar = util.criarBotao("Voltar", 75, 260, 250, 40, util.getCorBotaoSair());
        btnVoltar.addActionListener(e -> {
            dispose();
            new BibliotecarioTela().setVisible(true);
        });
        painel.add(btnVoltar);
    }
}
