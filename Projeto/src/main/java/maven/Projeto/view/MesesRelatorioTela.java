package maven.Projeto.view;

import maven.Projeto.util.ComponenteUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class MesesRelatorioTela extends JFrame {
    private static final long serialVersionUID = 1L;

    private ComponenteUtil util = new ComponenteUtil();

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


        JButton btnJulho2025 = util.criarBotao("Julho/2025", 100, 100, 200, 40, util.getCorBotaoPrincipal());
        //btnJulho2025.addActionListener(e -> relatoriosPDF.gerarEmprestimosMes());
        painel.add(btnJulho2025);

        JButton btnAgosto2025 = util.criarBotao("Agosto/2025", 320, 100, 200, 40, util.getCorBotaoPrincipal());
        //btnAgosto2025.addActionListener(e -> relatoriosPDF.gerarEmprestimosMes());
        painel.add(btnAgosto2025);
    }
}
