package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.FuncionarioDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.excepctions.FuncionarioNaoEncontradoException;
import maven.Projeto.excepctions.ValorNuloException;
import maven.Projeto.model.Funcionario;

/**
 * Classe responsável pelo controle de cadastro, autenticação e gerenciamento de funcionários.
 * Realiza validações e chama os métodos do DAO para persistência.
 * 
 * @author Hélder
 */
public class FuncionarioController {
	
	private FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
	
	/**
     * Verifica os dados informados e realiza o cadastro de um novo funcionário.
     */
	public void verificarCadastro(String id, String nome, String senha, String tipo) throws CampoVazioException {
        if (id == null || id.trim().isEmpty() || 
            nome == null || nome.trim().isEmpty() || 
            senha == null || senha.trim().isEmpty() || 
            tipo == null || tipo.trim().isEmpty()) {
            throw new CampoVazioException("Todos os campos devem ser preenchidos.");
        }
        
        Funcionario novo = new Funcionario();
        novo.setId(id);
        novo.setNome(nome);
        novo.setSenha(senha);
        novo.setTipo(tipo);
        
		funcionarioDAO.cadastrar(novo);
    }
	
	/**
     * Exclui um funcionário com base no ID informado.
     */
    public void excluirFuncionario(String id) throws CampoVazioException {
        if (id == null || id.isEmpty()) {
            throw new CampoVazioException("Informe o ID do funcionário para excluir.");
        }
        
        funcionarioDAO.excluir(id);
    }
    
    /**
     * Edita os dados de um funcionário existente.
     */
    public void editarFuncionario(String idAntigo, String id, String nome, String tipo, String senha) throws CampoVazioException {
        // Verificações básicas
        if (idAntigo == null || idAntigo.trim().isEmpty() || id == null || id.trim().isEmpty() || nome == null || nome.trim().isEmpty() || tipo == null || tipo.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new CampoVazioException("Todos os campos devem ser preenchidos.");
        }
        
        if (!tipo.equalsIgnoreCase("Administrador") &&
            !tipo.equalsIgnoreCase("Bibliotecário") &&
            !tipo.equalsIgnoreCase("Estagiário")) {
            throw new CampoVazioException("Tipo de usuário inválido.");
        }
        
        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(nome);
        novoFuncionario.setTipo(tipo);
        novoFuncionario.setId(id);
        novoFuncionario.setSenha(senha);
        
        funcionarioDAO.editarUsuario(idAntigo, novoFuncionario);
    }
    
    /**
     * Autentica o funcionário pelo ID e senha.
     */
    public Funcionario autenticarFuncionario(String id, String senha) throws CampoVazioException, FuncionarioNaoEncontradoException {
        if (id == null || id.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new CampoVazioException("ID e senha devem ser preenchidos.");
        }
        
        Funcionario funcionario = funcionarioDAO.buscarPorIdESenha(id, senha);
        
        if (funcionario == null) {
            throw new FuncionarioNaoEncontradoException("ID ou senha inválidos.");
        }
        
        return funcionario;
    }
    
    /**
     * Retorna o funcionário atualmente ativo no sistema.
     */
    public Funcionario BuscaFuncionarioAtivado() throws ValorNuloException {
    	Funcionario funcionario = funcionarioDAO.buscarFuncionarioAtivo();
    	
    	if (funcionario.isAtivo() == false) {
    		throw new ValorNuloException("O valor está nulo");
    	}
    	return funcionario;
    }
    
    /**
     * Desloga o funcionário ativo, marcando-o como inativo.
     */
    public void logoOffFuncionario() {
    	funcionarioDAO.deslogarFuncionarioAtivo();
    }
    
    /**
     * Retorna a lista de todos os funcionários registrados.
     */
    public List<Funcionario> getFuncionarios() {
    	funcionarioDAO.buscarArquivo(); 
        return funcionarioDAO.getLISTA_DE_FUNCIONARIOS();
    }
}
