package maven.Projeto.view;

import maven.Projeto.controller.FuncionarioController;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.excepctions.FuncionarioNaoEncontradoException;
import maven.Projeto.excepctions.ValorNuloException;
import maven.Projeto.model.Funcionario;

import javax.swing.*;
import java.awt.*;

public class LoginTela extends JFrame {
	FuncionarioController funcionarioController = new FuncionarioController();
    public LoginTela() {
        setTitle("Login | H² Biblioteca");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel painel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(45, 45, 45));
            }
        };
        painel.setLayout(null);
        add(painel);
        
        Font fontePadrao = new Font("SansSerif", Font.PLAIN, 15);
        
        JLabel titulo = new JLabel("H² Biblioteca", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 30, 420, 40);
        painel.add(titulo);
        
        JLabel subtitulo = new JLabel("Acesso ao sistema", SwingConstants.CENTER);
        subtitulo.setFont(new Font("SansSerif", Font.ITALIC, 14));
        subtitulo.setForeground(new Color(200, 200, 200));
        subtitulo.setBounds(0, 65, 420, 20);
        painel.add(subtitulo);
        
        JLabel idLabel = new JLabel("ID do Usuário:");
        idLabel.setBounds(60, 120, 120, 25);
        idLabel.setFont(fontePadrao);
        idLabel.setForeground(Color.WHITE);
        painel.add(idLabel);
        
        JTextField idField = new JTextField();
        idField.setFont(fontePadrao);
        idField.setBounds(180, 120, 180, 30);
        painel.add(idField);
        
        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(60, 170, 120, 25);
        senhaLabel.setFont(fontePadrao);
        senhaLabel.setForeground(Color.WHITE);
        painel.add(senhaLabel);
        
        JPasswordField senhaField = new JPasswordField();
        senhaField.setFont(fontePadrao);
        senhaField.setBounds(180, 170, 180, 30);
        painel.add(senhaField);
        
        JButton loginBtn = new JButton("Entrar");
        loginBtn.setBounds(140, 240, 120, 40);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        loginBtn.setBackground(new Color(220, 53, 69));
        loginBtn.setForeground(Color.WHITE);
        painel.add(loginBtn);
        
        loginBtn.addActionListener(e -> {
        	String id = idField.getText();
            String senha = new String(senhaField.getPassword());
            
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginTela().setVisible(true));
    }
}
