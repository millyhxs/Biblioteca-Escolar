package maven.Projeto.util;

import javax.swing.*;
import java.awt.*;

public class ComponenteUtil {

    public final Font FonteTitulo = new Font("Serif", Font.BOLD, 24);
    public final Font FontePadrao = new Font("SansSerif", Font.PLAIN, 14);
    public final Font FonteBotao = new Font("SansSerif", Font.BOLD, 14);
    public final Color CorFundoEscuro = new Color(40, 40, 40);
    public final Color CorBotaoPrincipal = new Color(220, 53, 69);
    public final Color CorBotaoSecundario = new Color (128, 128, 128);
    public final Color CorBotaoSair = new Color(100, 100, 100);
    public final Color CorTextoBranco = Color.WHITE;

    public JLabel criarLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, 100, 25);
        label.setForeground(CorTextoBranco);
        label.setFont(FontePadrao);
        return label;
    }

    public JTextField criarCampoTexto(int x, int y) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, 150, 25);
        campo.setFont(FontePadrao);
        return campo;
    }

    public JPasswordField criarCampoSenha(int x, int y) {
        JPasswordField campo = new JPasswordField();
        campo.setBounds(x, y, 150, 25);
        campo.setFont(FontePadrao);
        return campo;
    }

    public JButton criarBotao(String texto, int x, int y, int largura, int altura, Color corFundo) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, largura, altura);
        botao.setFont(FonteBotao);
        botao.setBackground(corFundo);
        botao.setForeground(CorTextoBranco);
        botao.setFocusPainted(false);
        return botao;
    }

    public JPanel painelComFundo(LayoutManager layout) {
        return new JPanel(layout) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(CorFundoEscuro);
            }
        };
    }

    public JPanel painelComFundoNulo() {
        return painelComFundo(null);
    }

    public void aplicarTemaPadrao(JFrame frame, String titulo, int largura, int altura) {
        frame.setTitle(titulo);
        frame.setSize(largura, altura);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
    }

	public Font getFonteTitulo() {
		return FonteTitulo;
	}

	public Font getFontePadrao() {
		return FontePadrao;
	}

	public Font getFonteBotao() {
		return FonteBotao;
	}

	public Color getCorFundoEscuro() {
		return CorFundoEscuro;
	}

	public Color getCorBotaoPrincipal() {
		return CorBotaoPrincipal;
	}

	public Color getCorBotaoSair() {
		return CorBotaoSair;
	}

	public Color getCorTextoBranco() {
		return CorTextoBranco;
	}

	public Color getCorBotaoSecundario() {
		return CorBotaoSecundario;
	}
    
} 
