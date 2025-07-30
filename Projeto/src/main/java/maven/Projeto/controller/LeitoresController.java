package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.LeitorDAO;
import maven.Projeto.exceptions.CampoVazioException;
import maven.Projeto.exceptions.MatriculaNaoEncontradaException;
import maven.Projeto.model.Leitor;

/**
 * Classe responsável pela lógica de controle relacionada aos leitores.
 * Realiza validações e interage com o DAO para operações de persistência.
 * 
 * @author Hélder
 */
public class LeitoresController {
	
	private LeitorDAO leitorDAO = new LeitorDAO();
	
	/**
     * Realiza a validação e o cadastro de um novo leitor.
     */
	public void verificacaoDeDados(String tipo, String matricula, String nome, String telefone, String email) throws CampoVazioException{
		if (matricula == null || matricula.trim().isEmpty()||
			nome == null || nome.trim().isEmpty() ||
			telefone == null || telefone.trim().isEmpty() ||
			email == null || email.trim().isEmpty()) {
			throw new CampoVazioException("Os campos não estão todos preenchidos."); 
		}
		
		String telefoneNumeros = telefone.replaceAll("\\D", "");
		
	    if (telefoneNumeros.length() < 11) {
	        throw new CampoVazioException("O número de telefone está incompleto.");
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
	
	/**
     * Exclui um leitor com base na matrícula informada.
     */
	public void exclusaoDeDados(String matricula) throws CampoVazioException {
		if (matricula == null || matricula.trim().isEmpty()) {
			throw new CampoVazioException("Preencha os espaços");
		}
		leitorDAO.excluir(matricula);
	}
	
	/**
     * Edita os dados de um leitor existente.
     */
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
	
	/**
     * Verifica se uma matrícula já existe no sistema.
     */
	public void verificarMatriculaExistente(String matricula) throws MatriculaNaoEncontradaException, CampoVazioException {
		if (matricula == null || matricula.trim().isEmpty()) {
			throw new CampoVazioException("Preencha todos os campos");
		}
		
		boolean existe = leitorDAO.verificarMatricula(matricula);		
		
	    if (!existe) {
	        throw new MatriculaNaoEncontradaException("Usuário não encontrado com essa matrícula.");
	    }
	}
	
	/**
     * Retorna a lista de todos os leitores cadastrados.
     */
	public List<Leitor> getLeitores() {
        leitorDAO.buscarArquivo(); 
        return leitorDAO.getLISTA_DE_USUARIOS();
    }
}
