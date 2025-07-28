package maven.Projeto.controller;

import maven.Projeto.dao.*;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class EmprestimoController {
    static LivroDAO livroDAO = new LivroDAO();
	static ArtigoDAO artigoDAO = new ArtigoDAO();
    static RevistaDAO revistaDAO = new RevistaDAO();
    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    
    public void registrarEmprestimo(String codigoObra, String matriculaUsuario, int diasDeEmprestimo, String responsavel) throws CampoVazioException {
        if (codigoObra == null || codigoObra.trim().isEmpty() ||
            matriculaUsuario == null || matriculaUsuario.trim().isEmpty() ||
            responsavel == null || responsavel.trim().isEmpty()) {
            throw new CampoVazioException("Todos os campos devem ser preenchidos.");
        }
        Emprestavel obra = buscarObraPorCodigo(codigoObra);
        
        if (obra == null) {
            System.out.println("Obra com o código " + codigoObra + " não encontrada.");
            return;
        }
        
        if (!obra.emprestar()) {
            System.out.println("Esta obra já está emprestada.");
            return;
        }
        
        atualizarObra(obra);
        
        Emprestimo emprestimo = new Emprestimo(codigoObra, matriculaUsuario, diasDeEmprestimo, responsavel);
        
		emprestimoDAO.cadastrar(emprestimo);
        
        System.out.println("Empréstimo registrado com sucesso!");
    }
    
    public void devolverObra(String codigoObra) {
        Emprestavel obra = buscarObraPorCodigo(codigoObra);
        
        if (obra == null) {
            System.out.println("Obra não encontrada.");
            return;
        }
        
        Emprestimo emprestimoEncontrado = null;
        
        for (Emprestimo e : emprestimoDAO.getEmprestimos()) {
            if (e.getCodigoObra().equals(codigoObra)) {
                emprestimoEncontrado = e;
                break;
            }
        }
        
        if (emprestimoEncontrado == null) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }	
        
        LocalDate dataDevolucao = LocalDate.now();
        if (dataDevolucao.isAfter(emprestimoEncontrado.getDataDevolucaoPrevista())) {
            long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(
                    emprestimoEncontrado.getDataDevolucaoPrevista(), dataDevolucao);
            float valorMulta = diasAtraso * emprestimoEncontrado.getTaxaDaMulta();
            
            System.out.println("Multa registrada: R$" + valorMulta);
        }
        
        if (!obra.devolver()) {
            System.out.println("A obra já está disponível.");
            return;
        }
        
        Devolucao devolucao = new Devolucao(
                emprestimoEncontrado.getCodigoObra(),
                emprestimoEncontrado.getMatriculaUsuario(),
                emprestimoEncontrado.getDataEmprestimo(), 
                LocalDate.now()
            );

            DevolucaoDAO devolucaoDAO = new DevolucaoDAO();
            devolucaoDAO.registrarDevolucao(devolucao);

            atualizarObra(obra);
            emprestimoDAO.excluirPorCodigo(codigoObra);

            System.out.println("Devolução registrada com sucesso.");
        }
    
    
    private Emprestavel buscarObraPorCodigo(String codigoObra) {
		livroDAO.buscarArquivo();
        for (Livro livro : livroDAO.LISTA_DE_OBRAS) {
            if (livro.getCodigo().equals(codigoObra)) {
                return livro;
            }
        }
        
		revistaDAO.buscarArquivo();
        for (Revista revista : revistaDAO.LISTA_DE_OBRAS) {
            if (revista.getCodigo().equals(codigoObra)) {
                return revista;
            }
        }
        
		artigoDAO.buscarArquivo();
        for (Artigo artigo : artigoDAO.LISTA_DE_OBRAS) {
            if (artigo.getCodigo().equals(codigoObra)) {
                return artigo;
            }
        }
        
        return null;
    }
    
    private void atualizarObra(Emprestavel obra) {
        if (obra instanceof Livro) {
            livroDAO.excluir(((Livro) obra).getCodigo());
            livroDAO.cadastrar((Livro) obra);
        } else if (obra instanceof Revista) {
            revistaDAO.excluir(((Revista) obra).getCodigo());
            revistaDAO.cadastrar((Revista) obra);
        } else if (obra instanceof Artigo) {
            artigoDAO.excluir(((Artigo) obra).getCodigo());
            artigoDAO.cadastrar((Artigo) obra);
        }
    }
    
    public float verificarMulta(Emprestimo emprestimo, int diasPermitidos) {
        return emprestimoDAO.calcularMultaParaEmprestimo(emprestimo, diasPermitidos);
    }
    
    public List<Emprestimo> getEmprestimos() {
    	emprestimoDAO.buscarArquivo();
        if (emprestimoDAO.LISTA_DE_EMPRESTIMOS == null) {
        	emprestimoDAO.LISTA_DE_EMPRESTIMOS = new ArrayList<>();
        }
        return emprestimoDAO.LISTA_DE_EMPRESTIMOS;
    }
}
