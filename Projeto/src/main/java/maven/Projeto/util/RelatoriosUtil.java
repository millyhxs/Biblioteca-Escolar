package maven.Projeto.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import maven.Projeto.controller.MultaDevolucaoController;
import maven.Projeto.controller.ObraController;
import maven.Projeto.dao.DevolucaoDAO;
import maven.Projeto.model.Devolucao;
import maven.Projeto.model.Obra;
import maven.Projeto.model.PagamentoMulta;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.List;

import javax.swing.JOptionPane;

public class RelatoriosUtil {
	
    private final String FONTE = FontFactory.HELVETICA;
    private final Font TITULO = new Font(FontFactory.getFont(FONTE, 18, Font.BOLD));
    private final Font TEXTO = new Font(FontFactory.getFont(FONTE, 15, Font.NORMAL));
    private final Font CABECALHO = new Font(FontFactory.getFont(FONTE, 15, Font.BOLD));
    
    //Empréstimos do mês atual
    public void gerarEmprestimosMes() {
    	Document doc = new Document();
        try {            
           
            PdfWriter.getInstance(doc, new FileOutputStream("relatorio_emprestimos_mes.pdf"));
            doc.open();
            
            int mesAtual = LocalDate.now().getMonthValue();
            int anoAtual = LocalDate.now().getYear();
            
            String nomeMes = LocalDate.now()
                    .getMonth()
                    .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
            
            doc.add(new Paragraph("Relatório: Empréstimos de " + nomeMes + " de " + anoAtual, TITULO));
            doc.add(new Paragraph(" "));
            DevolucaoDAO dao = new DevolucaoDAO();
            List<Devolucao> devolucoes = dao.getTodasDevolucoes();
            
            PdfPTable tabela = new PdfPTable(4);
            tabela.setWidthPercentage(100);
            adicionarCabecalho(tabela, "Código do livro", "Usuário", "Data Empréstimo", "Data Devolução");
            
            DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Devolucao d : devolucoes) {
                LocalDate dataEmprestimo = d.getDataEmprestimo();
                if (dataEmprestimo != null &&
                    dataEmprestimo.getMonthValue() == mesAtual &&
                    dataEmprestimo.getYear() == anoAtual) {
                    
                    tabela.addCell(new PdfPCell(new Phrase(d.getCodigoObra(), TEXTO)));
                    tabela.addCell(new PdfPCell(new Phrase(d.getMatriculaUsuario(), TEXTO)));
                    tabela.addCell(new PdfPCell(new Phrase(d.getDataEmprestimo().format(formatoBR), TEXTO)));
                    tabela.addCell(new PdfPCell(new Phrase(d.getDataDevolucao().format(formatoBR), TEXTO)));
                }
            }

            
            doc.add(tabela);
            JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso!");
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório: " + e.getMessage());
        } finally {
            doc.close();
            
            try {
                File pdfFile = new File("relatorio_emprestimos_mes.pdf");
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile); // Abre no leitor padrão do sistema
                } else {
                    JOptionPane.showMessageDialog(null, "Arquivo PDF não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao abrir o PDF: " + ex.getMessage());
            }
        }
    }
    
    //Obras mais emprestadas
    public void gerarObrasMaisEmprestadas() {
        Document document = new Document();
        try {
        	Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new AdaptadorLocalDate())
                    .create();
        	Type tipoLista = new TypeToken<List<Devolucao>>() {}.getType();
            FileReader leitor = new FileReader("listaDeDevolucoes.json");
            List<Devolucao> devolucoes = gson.fromJson(leitor, tipoLista);
            
            Map<String, Integer> contador = new HashMap<>();
            for (Devolucao d : devolucoes) {
                String codigo = d.getCodigoObra();
                contador.put(codigo, contador.getOrDefault(codigo, 0) + 1);
            }
            
            // Ordena do mais emprestado para o menos
            List<Map.Entry<String, Integer>> listaOrdenada = new ArrayList<>(contador.entrySet());
            listaOrdenada.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));
            
            PdfWriter.getInstance(document, new FileOutputStream("relatorio_obras_mais_emprestadas.pdf"));
            document.open();
            
            Paragraph titulo = new Paragraph("Obras Mais Emprestadas\n\n", TITULO);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            
            PdfPTable tabela = new PdfPTable(3);
            tabela.setWidths(new int[]{2, 5, 2});
            tabela.setWidthPercentage(100);
            
            tabela.addCell(new PdfPCell(new Phrase("Código", CABECALHO)));
            tabela.addCell(new PdfPCell(new Phrase("Título da Obra", CABECALHO)));
            tabela.addCell(new PdfPCell(new Phrase("Qtd. Empréstimos", CABECALHO)));
            
            for (Map.Entry<String, Integer> entrada : listaOrdenada) {
                String codigo = entrada.getKey();
                String tituloObra = buscarTituloDaObra(codigo);
                
                tabela.addCell(codigo);
                tabela.addCell(tituloObra);
                tabela.addCell(String.valueOf(entrada.getValue()));
            }

            document.add(tabela);
            JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório: " + e.getMessage());
        } finally {
            document.close();
            try {
                File pdfFile = new File("relatorio_obras_mais_emprestadas.pdf");
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    JOptionPane.showMessageDialog(null, "Arquivo PDF não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao abrir o PDF: " + ex.getMessage());
            }
        }
    }

    
    // Usuários com mais atrasos
    public void gerarUsuariosComMaisAtrasos() {
    	MultaDevolucaoController multaDevolucaoController = new MultaDevolucaoController();
        List<PagamentoMulta> pagamentos = multaDevolucaoController.listarTodosPagamentos();
        
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
            
            try {
                File pdfFile = new File("relatorio_usuarios_com_mais_atrasos.pdf");
                if (pdfFile.exists()) {
                    Desktop.getDesktop().open(pdfFile); // Abre no leitor padrão do sistema
                } else {
                    JOptionPane.showMessageDialog(null, "Arquivo PDF não encontrado.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao abrir o PDF: " + ex.getMessage());
            }
        }
    }
    private void adicionarCabecalho(PdfPTable tabela, String... titulos) {
        for (String t : titulos) {
            PdfPCell cell = new PdfPCell(new Phrase(t, CABECALHO));
            cell.setBackgroundColor(new BaseColor(200, 200, 200));
            tabela.addCell(cell);
        }
    }
    private String buscarTituloDaObra(String codigo) {
    	ObraController obraController = new ObraController();
        for (Obra o : obraController.getLivros()) {
            if (o.getCodigo().equals(codigo)) {
                return o.getTitulo();
            }
        }
        for (Obra o : obraController.getRevistas()) {
            if (o.getCodigo().equals(codigo)) {
                return o.getTitulo();
            }
        }
        for (Obra o : obraController.getArtigos()) {
            if (o.getCodigo().equals(codigo)) {
                return o.getTitulo();
            }
        }
        System.out.println("⚠️ Código não encontrado: " + codigo); // <--- Ajuda no debug
        return "Título não encontrado";
    }

    
}
