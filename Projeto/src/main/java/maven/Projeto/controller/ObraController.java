package maven.Projeto.controller;

import maven.Projeto.dao.ArtigoDAO;
import maven.Projeto.dao.LivroDAO;
import maven.Projeto.dao.RevistaDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Artigo;
import maven.Projeto.model.Livro;
import maven.Projeto.model.Revista;

public class ObraController {
	public void verificacaoDeDados(String opcao, String codigo, String titulo, String autor, String anoDePublicação, String emprestado) throws CampoVazioException{
		if (codigo == null || codigo.isEmpty()|| titulo == null || titulo.isEmpty() || autor == null || autor.isEmpty() || 
				anoDePublicação == null || anoDePublicação.isEmpty() || emprestado == null || emprestado.isEmpty()) {
			throw new CampoVazioException("Preencha todos os espaços."); 
		}
		
		if (opcao == "Livro") {
			Livro livro = new Livro();
			
			try {
				LivroDAO.cadastrar(livro);
			} catch (Exception e) {
				
			}
		}
		
		else if (opcao == "Revista") {
			Revista revista = new Revista();
			
			try {
				RevistaDAO.cadastrar(revista);
			} catch (Exception e) {
				
			}
		} 
		
		else if (opcao == "Artigo") {
			Artigo artigo = new Artigo();
			
			try {
				ArtigoDAO.cadastrar(artigo);
			} catch (Exception e) {
				
			}
		} 
	}
	
	public void exclusaoDeDados(String codigo) throws CampoVazioException {
		if (codigo == null || codigo.isEmpty()) {
			throw new CampoVazioException("Preencha os espaços");
		}
		else {
			try {
				
			} catch (Exception e) {
				
			}
		}
		
	}
}
