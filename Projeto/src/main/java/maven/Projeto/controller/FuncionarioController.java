package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.FuncionarioDAO;
import maven.Projeto.dao.LeitorDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Funcionario;
import maven.Projeto.model.Leitor;

public class FuncionarioController {
	
    public static void verificarCadastro(String id, String nome, String senha, String tipo) throws CampoVazioException {
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
        
        FuncionarioDAO.cadastrar(novo);
    }
    
    public static void excluirFuncionario(String id) throws CampoVazioException {
        if (id == null || id.isEmpty()) {
            throw new CampoVazioException("Informe o ID do funcionário para excluir.");
        }
        
        FuncionarioDAO.excluir(id);
    }
    
    public static void editarFuncionario(String idAntigo, String id, String nome, String tipo, String senha) throws CampoVazioException {
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
        
        FuncionarioDAO.editarUsuario(idAntigo, novoFuncionario);
    }
    
    public static List<Funcionario> getFuncionarios() {
    	 FuncionarioDAO.buscarArquivo(); 
         return FuncionarioDAO.LISTA_DE_FUNCIONARIOS;
    }
}
