package maven.Projeto.util;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Classe utilitária para aplicar filtros dinâmicos em tabelas (JTable).
 * Permite filtrar por uma ou várias colunas com base no texto digitado em um campo de entrada.
 * 
 * @author Millena
 */
public class FiltroTabelaUtil {

    private final TableRowSorter<DefaultTableModel> sorter;

    /**
     * Construtor que inicializa o sorter e o associa à tabela.
     */
    public FiltroTabelaUtil(JTable tabela) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        this.sorter = new TableRowSorter<>(modelo);
        tabela.setRowSorter(sorter);
    }
    
    /**
     * Aplica filtro em uma única coluna com base no conteúdo do JTextField.
     * O filtro atualiza automaticamente conforme o texto é digitado ou removido.
     */
    public void aplicarFiltro(JTextField campoFiltro, int coluna) {
        campoFiltro.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }

            private void filtrar() {
                String texto = campoFiltro.getText();
                if (texto.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, coluna));
                }
            }
        });
    }
    
    /**
     * Aplica filtro em múltiplas colunas simultaneamente com base no texto digitado.
     * Útil para buscas mais abrangentes (ex: pesquisar nome, ID e e-mail ao mesmo tempo).
     */ 
    public void aplicarFiltroMultiplo(JTextField campoFiltro, int[] colunas) {
        campoFiltro.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filtrar(); }
            public void removeUpdate(DocumentEvent e) { filtrar(); }
            public void changedUpdate(DocumentEvent e) { filtrar(); }

            private void filtrar() {
                String texto = campoFiltro.getText().trim();
                if (texto.length() == 0) {
                    sorter.setRowFilter(null);
                    return;
                }

                RowFilter<DefaultTableModel, Object> filtroComposto = RowFilter.regexFilter("(?i)" + texto, colunas);
                sorter.setRowFilter(filtroComposto);
            }
        });
    }

}