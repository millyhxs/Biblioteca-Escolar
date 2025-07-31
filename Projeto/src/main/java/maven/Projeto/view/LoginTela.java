package maven.Projeto.view;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.exceptions.CampoVazioException;
import maven.Projeto.exceptions.FuncionarioNaoEncontradoException;
import maven.Projeto.exceptions.ValorNuloException;
import maven.Projeto.model.Funcionario;
import maven.Projeto.util.ComponenteUtil;

import javax.swing.*;
import java.awt.*;

/**
 * Classe responsável por exibir a tela de login do sistema para funcionários.
 * Permite autenticação por meio de ID e senha.
 * @author Millena
 */
public class LoginTela extends JFrame {
	private static final long serialVersionUID = 1L;
	
	FuncionarioController funcionarioController = new FuncionarioController();
	private final ComponenteUtil util = new ComponenteUtil();
	private JTextField campoUsuario;
	private JPasswordField campoSenha;
	
	/**
	 * Construtor da classe LoginTela.
	 * Inicializa e configura os componentes da interface gráfica.
	 */
	
    public LoginTela() {
    	util.aplicarTemaPadrao(this, "Login", 400, 400);
        
        JPanel painel = util.painelComFundoNulo();
        painel.setLayout(null);
        getContentPane().add(painel);
        
        JLabel titulo = new JLabel("H² Biblioteca", SwingConstants.CENTER);
        titulo.setFont(util.getFonteTitulo());
        titulo.setForeground(util.getCorTextoBranco());
        titulo.setBounds(0, 30, 400, 40);
        painel.add(titulo);
        
        JLabel subtitulo = new JLabel("Acesso ao sistema", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.ITALIC, 14));
        subtitulo.setForeground(new Color(200, 200, 200));
        subtitulo.setBounds(0, 65, 400, 20);
        painel.add(subtitulo);
        
        JLabel labelUsuario = util.criarLabel("Usuário:", 90, 120);
        painel.add(labelUsuario);
        campoUsuario = util.criarCampoTexto(150, 120);
        painel.add(campoUsuario);
        campoUsuario.setFocusable(true);


        JLabel labelSenha = util.criarLabel("Senha:", 90, 160);
        painel.add(labelSenha);
        campoSenha = util.criarCampoSenha(150, 160);
        painel.add(campoSenha);

        JButton btnLogin = util.criarBotao("Entrar", 100, 240, 200, 40, util.getCorBotaoPrincipal());
        painel.add(btnLogin);
        getRootPane().setDefaultButton(btnLogin);


        btnLogin.addActionListener(e -> {
        	String id = campoUsuario.getText();
            String senha = new String(campoSenha.getPassword());
            
            try {
                funcionarioController.autenticarFuncionario(id, senha);
                
                Funcionario funcionario = funcionarioController.BuscaFuncionarioAtivado();
                
                if (funcionario != null) {
                    String nivel = funcionario.getTipo();
                    
                    switch (nivel) {
                        case "Administrador":
                            new AdministradorTela().setVisible(true);
                            break;
                        case "Bibliotecário":
                            new BibliotecarioTela().setVisible(true);
                            break;
                        case "Estagiário":
                            new EstagiarioTela().setVisible(true);
                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "Nível de acesso inválido.");
                            return;
                    }
                    
                    this.dispose();
                }
                
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (FuncionarioNaoEncontradoException ex) {
                JOptionPane.showMessageDialog(this, "Nenhum funcionário foi encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ValorNuloException ex) {
                JOptionPane.showMessageDialog(this, "Usuario nulo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });     
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Janela foi fechada!");
                
                funcionarioController.logoOffFuncionario();
            }
        });
    }
    
    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginTela().setVisible(true));
    }
}
