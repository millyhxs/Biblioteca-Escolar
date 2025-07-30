package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.ArtigoDAO;
import maven.Projeto.dao.LivroDAO;
import maven.Projeto.dao.RevistaDAO;
import maven.Projeto.exceptions.CampoVazioException;
import maven.Projeto.model.Artigo;
import maven.Projeto.model.Livro;
import maven.Projeto.model.Obra;
import maven.Projeto.model.Revista;

/**
 * Classe responsável por controlar as operações relacionadas às obras (livros, revistas e artigos).
 * Atua como intermediária entre a interface (View) e o acesso aos dados (DAO).
 * @author Hélder
 */
public class ObraController {
	
	private LivroDAO livroDAO = new LivroDAO();
	private ArtigoDAO artigoDAO = new ArtigoDAO();
	private RevistaDAO revistaDAO = new RevistaDAO();
	
	/**
     * Verifica os dados fornecidos e realiza o cadastro de uma obra dependendo do seu tipo.
     */
	public void verificacaoDeDados(String opcao, String codigoIgnorado, String titulo, String autor, String anoDePublicacao, boolean emprestado) throws CampoVazioException{
		if (titulo == null || titulo.trim().isEmpty() ||
			autor == null || autor.trim().isEmpty() ||
			anoDePublicacao == null || anoDePublicacao.trim().isEmpty()) {
			throw new CampoVazioException("Os campos não estão todos preenchidos."); 
		}
		
		if (opcao.equals("Livro")) {
			Livro livro = new Livro();
			
			try {
				livro.setCodigo(gerarCodigo("Livro"));
	            livro.setTitulo(titulo);
	            livro.setAutor(autor);
	            livro.setAnoDePublicacao(anoDePublicacao);
	            livro.setEmprestado(emprestado);
				livroDAO.cadastrar(livro);
			} catch (Exception e) {
				
			}
		}
		
		else if (opcao.equals("Revista")) {
			Revista revista = new Revista();
			
			try {
				revista.setCodigo(gerarCodigo("Revista"));
                revista.setTitulo(titulo);
                revista.setAutor(autor);
                revista.setAnoDePublicacao(anoDePublicacao);
                revista.setEmprestado(emprestado);
				revistaDAO.cadastrar(revista);
			} catch (Exception e) {
				
			}
		} 
		
		else if (opcao.equals("Artigo")) {
			Artigo artigo = new Artigo();
			
			try {
				 artigo.setCodigo(gerarCodigo("Artigo"));
	             artigo.setTitulo(titulo);
	             artigo.setAutor(autor);
	             artigo.setAnoDePublicacao(anoDePublicacao);
	             artigo.setEmprestado(emprestado);
				artigoDAO.cadastrar(artigo);
			} catch (Exception e) {
				
			}
		} 
	}
	
	public String gerarCodigo(String tipo) {
	    int proximoNumero = 1;
	    String prefixo = "";

	    if (tipo.equals("Livro")) {
	        prefixo = "L";
	        LivroDAO dao = new LivroDAO();
	        dao.buscarArquivo(); // garante lista atualizada
	        for (Livro l : dao.getLISTA_DE_OBRAS()) {
	            if (l.getCodigo().startsWith(prefixo)) {
	                int numero = Integer.parseInt(l.getCodigo().substring(1));
	                if (numero >= proximoNumero) proximoNumero = numero + 1;
	            }
	        }
	    } else if (tipo.equals("Revista")) {
	        prefixo = "R";
	        RevistaDAO dao = new RevistaDAO();
	        dao.buscarArquivo(); // garante lista atualizada
	        for (Revista r : dao.getLISTA_DE_OBRAS()) {
	            if (r.getCodigo().startsWith(prefixo)) {
	                int numero = Integer.parseInt(r.getCodigo().substring(1));
	                if (numero >= proximoNumero) proximoNumero = numero + 1;
	            }
	        }
	    } else if (tipo.equals("Artigo")) {
	        prefixo = "A";
	        ArtigoDAO dao = new ArtigoDAO();
	        dao.buscarArquivo(); // garante lista atualizada
	        for (Artigo a : dao.getLISTA_DE_OBRAS()) {
	            if (a.getCodigo().startsWith(prefixo)) {
	                int numero = Integer.parseInt(a.getCodigo().substring(1));
	                if (numero >= proximoNumero) proximoNumero = numero + 1;
	            }
	        }
	    }

	    return prefixo + String.format("%03d", proximoNumero);
	}



	
	/**
     * Exclui uma obra com base no tipo e código informado.
     */
	public void exclusaoDeDados(String tipo, String codigo) throws CampoVazioException {
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new CampoVazioException("Preencha os espaços");
		}
		switch (tipo) {
        case "Livro":
            livroDAO.excluir(codigo);
            break;
        case "Revista":
            revistaDAO.excluir(codigo);
            break;
        case "Artigo":
            artigoDAO.excluir(codigo);
            break;
        default:
            System.out.println("Tipo de obra inválido");
    }
		
	}
	
	/**
     * Retorna a lista de todos os livros cadastrados.
     */
	public List<Livro> getLivros() {
        LivroDAO livroDAO = new LivroDAO();
		livroDAO.buscarArquivo(); 
        return livroDAO.getLISTA_DE_OBRAS();
    }
	
	/**
     * Retorna a lista de todas as revistas cadastradas.
     */
	public List<Revista> getRevistas() {
        RevistaDAO revistaDAO = new RevistaDAO();
		revistaDAO.buscarArquivo(); 
        return revistaDAO.getLISTA_DE_OBRAS();
    }
	
	/**
     * Retorna a lista de todas os artigos cadastradps.
     */
	public List<Artigo> getArtigos() {
        ArtigoDAO artigoDAO = new ArtigoDAO();
		artigoDAO.buscarArquivo(); 
        return artigoDAO.getLISTA_DE_OBRAS();
    }
}
