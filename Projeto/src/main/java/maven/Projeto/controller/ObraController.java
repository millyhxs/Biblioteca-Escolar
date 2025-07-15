package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.ArtigoDAO;
import maven.Projeto.dao.LivroDAO;
import maven.Projeto.dao.RevistaDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Artigo;
import maven.Projeto.model.Livro;
import maven.Projeto.model.Revista;

public class ObraController {
	public static void verificacaoDeDados(String opcao, String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) throws CampoVazioException{
		if (codigo == null || codigo.trim().isEmpty()|| titulo == null || titulo.trim().isEmpty() || autor == null || autor.trim().isEmpty() || anoDePublicacao == null || anoDePublicacao.trim().isEmpty()) {
			throw new CampoVazioException("Os campos não estão todos preenchidos."); 
		}
		
		if (opcao.equals("Livro")) {
			Livro livro = new Livro();
			
			try {
				livro.setCodigo(codigo);
	            livro.setTitulo(titulo);
	            livro.setAutor(autor);
	            livro.setAnoDePublicacao(anoDePublicacao);
	            livro.setEmprestado(emprestado);
				LivroDAO.cadastrar(livro);
			} catch (Exception e) {
				
			}
		}
		
		else if (opcao.equals("Revista")) {
			Revista revista = new Revista();
			
			try {
				revista.setCodigo(codigo);
                revista.setTitulo(titulo);
                revista.setAutor(autor);
                revista.setAnoDePublicacao(anoDePublicacao);
                revista.setEmprestado(emprestado);
				RevistaDAO.cadastrar(revista);
			} catch (Exception e) {
				
			}
		} 
		
		else if (opcao.equals("Artigo")) {
			Artigo artigo = new Artigo();
			
			try {
				 artigo.setCodigo(codigo);
	             artigo.setTitulo(titulo);
	             artigo.setAutor(autor);
	             artigo.setAnoDePublicacao(anoDePublicacao);
	             artigo.setEmprestado(emprestado);
				ArtigoDAO.cadastrar(artigo);
			} catch (Exception e) {
				
			}
		} 
	}
	
	public static void exclusaoDeDados(String tipo, String codigo) throws CampoVazioException {
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new CampoVazioException("Preencha os espaços");
		}
		switch (tipo) {
        case "Livro":
            LivroDAO.excluir(codigo);
            break;
        case "Revista":
            RevistaDAO.excluir(codigo);
            break;
        case "Artigo":
            ArtigoDAO.excluir(codigo);
            break;
        default:
            System.out.println("Tipo de obra inválido");
    }
		
	}
	
	public static List<Livro> getLivros() {
        LivroDAO.buscarArquivo(); 
        return LivroDAO.LISTA_DE_OBRAS;
    }
	
	public static List<Revista> getRevistas() {
        RevistaDAO.buscarArquivo(); 
        return RevistaDAO.LISTA_DE_OBRAS;
    }
	
	public static List<Artigo> getArtigos() {
        ArtigoDAO.buscarArquivo(); 
        return ArtigoDAO.LISTA_DE_OBRAS;
    }
}
