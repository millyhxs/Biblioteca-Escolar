package maven.Projeto.view;

import maven.Projeto.controller.ObraController;
import maven.Projeto.dao.ArtigoDAO;
import maven.Projeto.dao.LivroDAO;
import maven.Projeto.dao.RevistaDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Artigo;
import maven.Projeto.model.Livro;
import maven.Projeto.model.Revista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaCadastroObras extends JFrame {
    private JComboBox<String> tipoCombo;
    private JTextField codField, tituloField, autorField, anoField;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ObraController controller = new ObraController();

    public TelaCadastroObras() {
        setTitle("Cadastro de Obras");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(null) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(45, 45, 45));
            }
        };

        add(painel);

        JLabel titulo = new JLabel("Cadastro de Obras", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 10, 700, 30);
        painel.add(titulo);

        JLabel tipoLabel = criarLabel("Tipo:", 30, 60);
        painel.add(tipoLabel);
        tipoCombo = new JComboBox<>(new String[]{"Livro", "Revista", "Artigo"});
        tipoCombo.setBounds(100, 60, 150, 25);
        painel.add(tipoCombo);

        painel.add(criarLabel("Código:", 270, 60));
        codField = criarCampoTexto(340, 60);
        painel.add(codField);

        painel.add(criarLabel("Título:", 30, 100));
        tituloField = criarCampoTexto(100, 100);
        painel.add(tituloField);

        painel.add(criarLabel("Autor:", 270, 100));
        autorField = criarCampoTexto(340, 100);
        painel.add(autorField);

        painel.add(criarLabel("Ano:", 510, 100));
        anoField = criarCampoTexto(550, 100);
        painel.add(anoField);

        JButton adicionarBtn = new JButton("Adicionar Obra");
        adicionarBtn.setBounds(270, 140, 160, 30);
        painel.add(adicionarBtn);

        adicionarBtn.addActionListener(e -> {
            try {
                String tipo = (String) tipoCombo.getSelectedItem();
                String codigo = codField.getText();
                String tituloTxt = tituloField.getText();
                String autor = autorField.getText();
                String ano = anoField.getText();

                controller.verificacaoDeDados(tipo, codigo, tituloTxt, autor, ano, "Disponível");
                JOptionPane.showMessageDialog(this, "Obra adicionada com sucesso!");
                atualizarTabela();
            } catch (CampoVazioException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        modeloTabela = new DefaultTableModel(new String[]{"Código", "Título", "Autor", "Ano", "Tipo"}, 0);
        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(30, 200, 620, 230);
        painel.add(scroll);

        atualizarTabela();
    }

    private JLabel criarLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, 100, 25);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField criarCampoTexto(int x, int y) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, 150, 25);
        return campo;
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        for (Livro livro : LivroDAO.getLivros()) {
            modeloTabela.addRow(new Object[]{livro.getCodigo(), livro.getTitulo(), livro.getAutor(), livro.getAnoDePublicacao(), "Livro"});
        }
        for (Revista revista : RevistaDAO.getRevistas()) {
            modeloTabela.addRow(new Object[]{revista.getCodigo(), revista.getTitulo(), revista.getAutor(), revista.getAnoDePublicacao(), "Revista"});
        }
        for (Artigo artigo : ArtigoDAO.getArtigos()) {
            modeloTabela.addRow(new Object[]{artigo.getCodigo(), artigo.getTitulo(), artigo.getAutor(), artigo.getAnoDePublicacao(), "Artigo"});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroObras().setVisible(true));
    }
}