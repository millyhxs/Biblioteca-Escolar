package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.LeitorDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.excepctions.MatriculaNaoEncontradaException;
import maven.Projeto.model.Leitor;

public class LeitoresController {
	private LeitorDAO leitorDAO = new LeitorDAO();
	public void verificacaoDeDados(String tipo, String matricula, String nome, String telefone, String email) throws CampoVazioException{
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
			leitorDAO.cadastrar(leitor);
		} catch (Exception e) {
			
		} 
	}
	
	public void exclusaoDeDados(String codigo) throws CampoVazioException {
		if (codigo == null || codigo.trim().isEmpty()) {
			throw new CampoVazioException("Preencha os espaços");
		}
		leitorDAO.excluir(codigo);
	}
	
	public void editarUsuario(String matriculaAntiga, String matricula, String nome, String tipoDeUsuario, String telefone, String email) throws CampoVazioException {
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
        
        leitorDAO.editarUsuario(matriculaAntiga, novoLeitor);
    }
	
	public void verificarMatriculaExistente(String matricula) throws MatriculaNaoEncontradaException, CampoVazioException {
		if (matricula == null || matricula.trim().isEmpty()) {
			throw new CampoVazioException("Preencha todos os campos");
		}
		
		boolean existe = leitorDAO.verificarMatricula(matricula);		
		
	    if (!existe) {
	        throw new MatriculaNaoEncontradaException("Usuário não encontrado com essa matrícula.");
	    }
	}
	
	public List<Leitor> getLeitores() {
        leitorDAO.buscarArquivo(); 
        return leitorDAO.getLISTA_DE_USUARIOS();
    }
}
