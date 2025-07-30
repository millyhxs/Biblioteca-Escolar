package maven.Projeto.view;

import maven.Projeto.util.ComponenteUtil;
import maven.Projeto.util.RelatoriosUtil;

import javax.swing.*;

public class MesesRelatorioTela extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private ComponenteUtil util = new ComponenteUtil();
    
    private RelatoriosUtil relatorio = new RelatoriosUtil();
    
    public MesesRelatorioTela() {
        util.aplicarTemaPadrao(this, "Relatório de Empréstimos por Mês", 700, 500);
        
        JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("Relatórios Mensais de Empréstimos", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 20, 700, 30);
        painel.add(titulo);
        
        
        JButton btnJulho2025 = util.criarBotao("Julho/2025", 100, 100, 150, 40, util.getCorBotaoPrincipal());
        
        btnJulho2025.addActionListener(e -> relatorio.gerarEmprestimosMes(7, 2025));
        painel.add(btnJulho2025);
        
        JButton btnAgosto2025 = util.criarBotao("Agosto/2025", 270, 100, 150, 40, util.getCorBotaoPrincipal());
        
        btnAgosto2025.addActionListener(e -> relatorio.gerarEmprestimosMes(8, 2025));
        painel.add(btnAgosto2025);
        
        JButton btnSetembro2025 = util.criarBotao("Setembro/2025", 440, 100, 150, 40, util.getCorBotaoPrincipal());
        
        btnSetembro2025.addActionListener(e -> relatorio.gerarEmprestimosMes(9, 2025));
        painel.add(btnSetembro2025);
    }
    
}
