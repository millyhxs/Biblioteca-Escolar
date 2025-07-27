package maven.Projeto.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import maven.Projeto.dao.EmprestimoDAO;
import maven.Projeto.dao.MultaDAO;
import maven.Projeto.model.Emprestimo;
import maven.Projeto.model.PagamentoMulta;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import javax.swing.JOptionPane;

public class RelatoriosPDF {

    private static final String FONTE = FontFactory.HELVETICA;
    private static final Font TITULO = new Font(FontFactory.getFont(FONTE, 18, Font.BOLD));
    private static final Font TEXTO = new Font(FontFactory.getFont(FONTE, 15, Font.NORMAL));
    private static final Font CABECALHO = new Font(FontFactory.getFont(FONTE, 15, Font.BOLD));

    //Empréstimos do mês atual
    public static void gerarEmprestimosMes() {
    	 Document doc = new Document();
        try {            
           
            PdfWriter.getInstance(doc, new FileOutputStream("relatorio_emprestimos_mes.pdf"));
            doc.open();

            doc.add(new Paragraph("Relatório: Empréstimos do Mês", TITULO));
            doc.add(new Paragraph(" "));

            List<Emprestimo> emprestimos = EmprestimoDAO.getEmprestimos();
            int mesAtual = LocalDate.now().getMonthValue();
            int anoAtual = LocalDate.now().getYear();

            PdfPTable tabela = new PdfPTable(3);
            tabela.setWidthPercentage(100);
            adicionarCabecalho(tabela, "Código", "Usuário", "Data Empréstimo", "Data Devolução");

            for (Emprestimo e : emprestimos) {
                if (e.getDataEmprestimo().getMonthValue() == mesAtual && e.getDataEmprestimo().getYear() == anoAtual) {
                    tabela.addCell(new PdfPCell(new Phrase(e.getCodigoObra(), TEXTO)));
                    tabela.addCell(new PdfPCell(new Phrase(e.getMatriculaUsuario(), TEXTO)));
                    tabela.addCell(new PdfPCell(new Phrase(e.getDataEmprestimo().toString(), TEXTO)));
                    tabela.addCell(new PdfPCell(new Phrase(e.getDataDevolucaoPrevista().toString(), TEXTO)));
                }
            }

            doc.add(tabela);
            JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório: " + e.getMessage());
        } finally {
            doc.close();
        }
    }

    //Obras mais emprestadas
    public static Object gerarObrasMaisEmprestadas() {
		// TODO Auto-generated method stub
		return null;
	}
    
    // Usuários com mais atrasos
    public static void gerarUsuariosComMaisAtrasos() {
        List<PagamentoMulta> pagamentos = MultaDAO.getTodosPagamentos();
        
        if (pagamentos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum pagamento de multa registrado.");
            return;
        }

        Map<String, Integer> contagemAtrasos = new HashMap<>();

        for (PagamentoMulta pagamento : pagamentos) {
            String matricula = pagamento.getMatriculaUsuario();
            contagemAtrasos.put(matricula, contagemAtrasos.getOrDefault(matricula, 0) + 1);
        }

        // Ordenar pelo número de atrasos, do maior pro menor
        List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(contagemAtrasos.entrySet());
        listaOrdenada.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("relatorio_usuarios_com_mais_atrasos.pdf"));
            document.open();

            Font tituloFonte = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Usuários com Mais Atrasos\n\n", tituloFonte);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            Font cabecalhoFonte = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            PdfPTable tabela = new PdfPTable(2);
            tabela.setWidths(new int[]{2, 1});
            tabela.setWidthPercentage(100);

            tabela.addCell(new PdfPCell(new Phrase("Matrícula do Usuário", cabecalhoFonte)));
            tabela.addCell(new PdfPCell(new Phrase("Quantidade de Atrasos", cabecalhoFonte)));

            for (Map.Entry<String, Integer> entrada : listaOrdenada) {
                tabela.addCell(entrada.getKey());
                tabela.addCell(String.valueOf(entrada.getValue()));
            }

            document.add(tabela);
            JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório: " + e.getMessage());
        } finally {
            document.close();
        }
    }
    private static void adicionarCabecalho(PdfPTable tabela, String... titulos) {
        for (String t : titulos) {
            PdfPCell cell = new PdfPCell(new Phrase(t, CABECALHO));
            cell.setBackgroundColor(new BaseColor(200, 200, 200));
            tabela.addCell(cell);
        }
    }

}
