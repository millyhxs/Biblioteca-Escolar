package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.LeitorDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Leitor;

public class LeitoresController {
	public static void verificacaoDeDados(String tipo, String matricula, String nome, String telefone, String email) throws CampoVazioException{
		if (matricula == null || matricula.trim().isEmpty()|| nome == null || nome.trim().isEmpty() || telefone == null || telefone.trim().isEmpty() || email == null || email.trim().isEmpty()) {
			throw new CampoVazioException("Os campos não estão todos preenchidos."); 
		}
		
		try {
			Leitor leitor = new Leitor();
			leitor.setTipoDeUsuario(tipo);
			leitor.setMatricula(matricula);
			leitor.setNome(nome);
			leitor.setTelefone(telefone);
			leitor.setEmail(email);
			LeitorDAO.cadastrar(leitor);
		} catch (Exception e) {
			
		} 
	}
	
	public static void exclusaoDeDados(String codigo) throws CampoVazioException {
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new CampoVazioException("Preencha os espaços");
		}
		LeitorDAO.excluir(codigo);
	}
	
	public static List<Leitor> getLeitores() {
        LeitorDAO.buscarArquivo(); 
        return LeitorDAO.LISTA_DE_USUARIOS;
    }
}
