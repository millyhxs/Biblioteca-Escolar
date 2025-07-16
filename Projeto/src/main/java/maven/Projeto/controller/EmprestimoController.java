package maven.Projeto.controller;

import maven.Projeto.dao.*;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.*;

import java.time.LocalDate;
import java.util.List;

public class EmprestimoController {

    public static void registrarEmprestimo(String codigoObra, String matriculaUsuario, String responsavel) throws CampoVazioException {
        if (codigoObra == null || codigoObra.trim().isEmpty() ||
            matriculaUsuario == null || matriculaUsuario.trim().isEmpty() ||
            responsavel == null || responsavel.trim().isEmpty()) {
            throw new CampoVazioException("Todos os campos devem ser preenchidos.");
        }

    }
}