package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.LeitorDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Leitor;

public class LeitoresController {
	public static void verificacaoDeDados(String tipo, String matricula, String nome, String telefone, String email) throws CampoVazioException{
		if (matricula == null || matricula.trim().isEmpty()||
			nome == null || nome.trim().isEmpty() ||
			telefone == null || telefone.trim().isEmpty() ||
			email == null || email.trim().isEmpty()) {
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
	
	public static void editarUsuario(String matriculaAntiga, String matricula, String nome, String tipoDeUsuario, String telefone, String email) throws CampoVazioException {
        // Verificações básicas
        if (matriculaAntiga == null || matriculaAntiga.trim().isEmpty() || matricula == null || matricula.trim().isEmpty() || nome == null || nome.trim().isEmpty() || tipoDeUsuario == null || tipoDeUsuario.trim().isEmpty() || telefone == null || telefone.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            throw new CampoVazioException("Todos os campos devem ser preenchidos.");
        }
        
        if (!tipoDeUsuario.equalsIgnoreCase("Aluno") &&
            !tipoDeUsuario.equalsIgnoreCase("Professor") &&
            !tipoDeUsuario.equalsIgnoreCase("Servidor")) {
            throw new CampoVazioException("Tipo de usuário inválido.");
        }
        
        Leitor novoLeitor = new Leitor();
        
        novoLeitor.setMatricula(matricula);
        novoLeitor.setNome(nome);
        novoLeitor.setTipoDeUsuario(tipoDeUsuario);
        novoLeitor.setTelefone(telefone);
        novoLeitor.setEmail(email);
        
        LeitorDAO.editarUsuario(matriculaAntiga, novoLeitor);
    }
	
	
	public static List<Leitor> getLeitores() {
        LeitorDAO.buscarArquivo(); 
        return LeitorDAO.LISTA_DE_USUARIOS;
    }
}
