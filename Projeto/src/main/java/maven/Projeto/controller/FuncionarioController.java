package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.FuncionarioDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Funcionario;

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

    public static List<Funcionario> getFuncionarios() {
    	 FuncionarioDAO.buscarArquivo(); 
         return FuncionarioDAO.LISTA_DE_FUNCIONARIOS;
    }
}
