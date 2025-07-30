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
import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Classe utilitária responsável pela geração de relatórios em PDF relacionados ao sistema de biblioteca.
 * Relatórios gerados:
 * 
 * @author Millena
 */
public class RelatoriosUtil {
	
    private final String FONTE = FontFactory.HELVETICA;
    private final Font TITULO = new Font(FontFactory.getFont(FONTE, 18, Font.BOLD));
    private final Font TEXTO = new Font(FontFactory.getFont(FONTE, 15, Font.NORMAL));
    private final Font CABECALHO = new Font(FontFactory.getFont(FONTE, 15, Font.BOLD));
    
    /**
     * Gera um relatório PDF com todos os empréstimos realizados de acordo com o mes escolhido.
     * 
     * @param mesEscolhido Mês que será mostrado no PDF e usado como base para a filtragem de Empréstimos.
     * @param anoEscolhido Ano que será mostrado no PDF.
     */
    public void gerarEmprestimosMes(int mesEscolhido, int anoEscolhido) {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream("relatorio_emprestimos_mes.pdf"));
            doc.open();
            
            YearMonth anoMes = YearMonth.of(anoEscolhido, mesEscolhido);
            
            String mesEAno = anoMes.format(DateTimeFormatter.ofPattern("MMMM/yyyy", new Locale("pt", "BR")));
            
            mesEAno = mesEAno.substring(0, 1).toUpperCase() + mesEAno.substring(1);
            
            Paragraph titulo = new Paragraph("Empréstimos do mês - " + mesEAno, TITULO);
            titulo.setSpacingAfter(10);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);
            
            DevolucaoDAO dao = new DevolucaoDAO();
            List<Devolucao> devolucoes = dao.getTodasDevolucoes();
            
            PdfPTable tabela = new PdfPTable(4);
            tabela.setWidthPercentage(100);
            adicionarCabecalho(tabela, "Código do livro", "Usuário", "Data Empréstimo", "Data Devolução");
            
            DateTimeFormatter formatoBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (Devolucao d : devolucoes) {
                LocalDate dataEmprestimo = d.getDataEmprestimo();
                if (dataEmprestimo != null &&
                    dataEmprestimo.getMonthValue() == mesEscolhido &&
                    dataEmprestimo.getYear() == anoEscolhido) {
                	
                    tabela.addCell(new PdfPCell(new Phrase(d.getCodigoObra(), TEXTO)));
                    tabela.addCell(new PdfPCell(new Phrase(d.getMatriculaUsuario(), TEXTO)));
                    tabela.addCell(new PdfPCell(new Phrase(dataEmprestimo.format(formatoBR), TEXTO)));
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
            abrirPdf("relatorio_emprestimos_mes.pdf");
        }
    }
    
    /**
     * Gera um relatório com as obras mais emprestadas (ordenadas do maior para o menor).
     */
    public void gerarObrasMaisEmprestadas() {
        Document document = new Document();
        try {
        	Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new AdaptadorDataUtil())
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
            
            Paragraph titulo = new Paragraph("Obras Mais Emprestadas", TITULO);
            titulo.setSpacingAfter(10);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            
            PdfPTable tabela = new PdfPTable(3);
            tabela.setWidths(new int[]{2, 5, 2});
            tabela.setWidthPercentage(100);
            
            adicionarCabecalho(tabela, "Código", "Título da Obra", "Qtd. Empréstimos");
            
            for (Map.Entry<String, Integer> entrada : listaOrdenada) {
            	tabela.addCell(entrada.getKey());
                tabela.addCell(buscarTituloDaObra(entrada.getKey()));
                tabela.addCell(String.valueOf(entrada.getValue()));
            }
            
            document.add(tabela);
            JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso!");
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório: " + e.getMessage());
        } finally {
            document.close();
            abrirPdf("relatorio_obras_mais_emprestadas.pdf");
        }
    }
    
    /**
     * Gera um relatório com os usuários que mais atrasaram devoluções.
     */
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
            
            Paragraph titulo = new Paragraph("Usuários com Mais Atrasos", TITULO);
            titulo.setSpacingAfter(10); 
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            
            PdfPTable tabela = new PdfPTable(2);
            tabela.setWidths(new int[]{2, 2});
            tabela.setWidthPercentage(100);
            adicionarCabecalho(tabela, "Matrícula do Usuário", "Quantidade de Atrasos");
            
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
            
            abrirPdf("relatorio_usuarios_com_mais_atrasos.pdf");
        }
    }
    
    /**
     * Adiciona cabeçalho a uma tabela PDF com base nos títulos fornecidos.
     */
    private void adicionarCabecalho(PdfPTable tabela, String... titulos) {
        for (String t : titulos) {
            PdfPCell cell = new PdfPCell(new Phrase(t, CABECALHO));
            cell.setBackgroundColor(new BaseColor(200, 200, 200));
            tabela.addCell(cell);
        }
    }
    
    /**
     * Busca o título da obra baseado no código.
     */
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
    
    /**
     * Abre o PDF automaticamente no leitor padrão do sistema.
     * 
     * @param caminho Caminho que será utilizado para abrir o PDF.
     */
    private void abrirPdf(String caminho) {
        try {
            File pdfFile = new File(caminho);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                JOptionPane.showMessageDialog(null, "Arquivo PDF não encontrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao abrir o PDF: " + e.getMessage());
        }
    }
    
}
