package maven.Projeto.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TelaCadastroObra extends JFrame {

    public TelaCadastroObra() {
        setTitle("Cadastro de Obras");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centraliza
        
        // fundo
        JPanel painel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(139, 0, 0)); // vermelho escuro
            }
        };
        painel.setLayout(null);
        add(painel);
        
        // estilo padrão dos textos
        Font fontePadrao = new Font("SansSerif", Font.PLAIN, 14);
        Color corTexto = Color.BLACK;
        
        // ttítulo
        JLabel titulo = new JLabel("H² Biblioteca", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 20, 500, 40);
        painel.add(titulo);
        
        // tipo de obra
        JLabel tipoLabel = new JLabel("Tipo de Obra:");
        tipoLabel.setBounds(50, 80, 100, 25);
        tipoLabel.setFont(fontePadrao);
        tipoLabel.setForeground(corTexto);
        painel.add(tipoLabel);
        
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Livro", "Revista", "Artigo"}); // lista suspensa
        tipoCombo.setBounds(160, 80, 200, 25);
        tipoCombo.setFont(fontePadrao);
        tipoCombo.setBackground(new Color(255, 255, 255, 200)); // semi-transparente
        tipoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            
            // feito pra centralizar o texto no jcombobox
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        });
        painel.add(tipoCombo);
        
        // código
        JLabel codLabel = new JLabel("Código:");
        codLabel.setBounds(50, 120, 100, 25);
        codLabel.setFont(fontePadrao);
        codLabel.setForeground(corTexto);
        painel.add(codLabel);
        
        JTextField codField = criarCaixaTexto();
        codField.setBounds(160, 120, 200, 25);
        painel.add(codField);
        
        // título
        JLabel tituloLabel = new JLabel("Título:");
        tituloLabel.setBounds(50, 160, 100, 25);
        tituloLabel.setFont(fontePadrao);
        tituloLabel.setForeground(corTexto);
        painel.add(tituloLabel);
        
        JTextField tituloField = criarCaixaTexto();
        tituloField.setBounds(160, 160, 200, 25);
        painel.add(tituloField);
        
        // autor
        JLabel autorLabel = new JLabel("Autor:");
        autorLabel.setBounds(50, 200, 100, 25);
        autorLabel.setFont(fontePadrao);
        autorLabel.setForeground(corTexto);
        painel.add(autorLabel);
        
        JTextField autorField = criarCaixaTexto();
        autorField.setBounds(160, 200, 200, 25);
        painel.add(autorField);
        
        // ano 
        JLabel anoLabel = new JLabel("Ano:");
        anoLabel.setBounds(50, 240, 100, 25);
        anoLabel.setFont(fontePadrao);
        anoLabel.setForeground(corTexto);
        painel.add(anoLabel);
        
        JTextField anoField = criarCaixaTexto();
        anoField.setBounds(160, 240, 100, 25); 
        
        // verifica se o caractere é numero, colocar um aviso na tela dps?
        anoField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) e.consume();
            }
        });
        painel.add(anoField);
        
        // status
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setBounds(50, 280, 100, 25);
        statusLabel.setFont(fontePadrao);
        statusLabel.setForeground(corTexto);
        painel.add(statusLabel);
        
        JRadioButton disponivel = new JRadioButton("Disponível");
        disponivel.setBounds(160, 280, 100, 25);
        disponivel.setFont(fontePadrao);
        disponivel.setOpaque(false);
        
        JRadioButton emprestado = new JRadioButton("Emprestado");
        emprestado.setBounds(270, 280, 120, 25);
        emprestado.setFont(fontePadrao);
        emprestado.setOpaque(false);
        
        ButtonGroup grupoStatus = new ButtonGroup();
        grupoStatus.add(disponivel);
        grupoStatus.add(emprestado);
        
        painel.add(disponivel);
        painel.add(emprestado);
        
        // botão finalizar
        JButton finalizarBtn = new JButton("Finalizar");
        finalizarBtn.setBounds(180, 340, 120, 35);
        finalizarBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        painel.add(finalizarBtn);
    }
    
    // método das caixas de texto
    private JTextField criarCaixaTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campo.setBackground(new Color(255, 255, 255, 200)); // transparente
        campo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return campo;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaCadastroObra().setVisible(true);
        });
    }
}