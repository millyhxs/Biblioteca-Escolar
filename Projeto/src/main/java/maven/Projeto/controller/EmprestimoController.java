package maven.Projeto.controller;

import maven.Projeto.dao.EmprestimoDAO;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.Emprestimo;

public class EmprestimoController {

    public static void registrarEmprestimo(String codigoObra, String matriculaUsuario, int diasDeEmprestimo, String responsavel) throws CampoVazioException {
        if (codigoObra == null || codigoObra.trim().isEmpty() ||
            matriculaUsuario == null || matriculaUsuario.trim().isEmpty() ||
            responsavel == null || responsavel.trim().isEmpty()) {
            throw new CampoVazioException("Todos os campos devem ser preenchidos.");
        }
        Emprestimo emprestimo = new Emprestimo(codigoObra, matriculaUsuario, diasDeEmprestimo, responsavel);
        EmprestimoDAO.cadastrar(emprestimo);
    }
}