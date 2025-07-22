package maven.Projeto.controller;

import maven.Projeto.dao.*;
import maven.Projeto.excepctions.CampoVazioException;
import maven.Projeto.model.*;
import java.time.LocalDate;
import java.util.UUID;
public class EmprestimoController {

    public static void registrarEmprestimo(String codigoObra, String matriculaUsuario, int diasDeEmprestimo, String responsavel) throws CampoVazioException {
        if (codigoObra == null || codigoObra.trim().isEmpty() ||
            matriculaUsuario == null || matriculaUsuario.trim().isEmpty() ||
            responsavel == null || responsavel.trim().isEmpty()) {
            throw new CampoVazioException("Todos os campos devem ser preenchidos.");
        }
        Emprestavel obra = buscarObraPorCodigo(codigoObra);

        if (obra == null) {
            System.out.println("Obra com o código " + codigoObra + " não encontrada.");
            return;
        }

        if (!obra.emprestar()) {
            System.out.println("Esta obra já está emprestada.");
            return;
        }

        atualizarObra(obra);

        Emprestimo emprestimo = new Emprestimo(codigoObra, matriculaUsuario, diasDeEmprestimo, responsavel);
        EmprestimoDAO.cadastrar(emprestimo);

        System.out.println("Empréstimo registrado com sucesso!");
    }

    public static void devolverObra(String codigoObra) {
        Emprestavel obra = buscarObraPorCodigo(codigoObra);

        if (obra == null) {
            System.out.println("Obra não encontrada.");
            return;
        }

        Emprestimo emprestimoEncontrado = null;

        for (Emprestimo e : EmprestimoDAO.getEmprestimos()) {
            if (e.getCodigoObra().equals(codigoObra)) {
                emprestimoEncontrado = e;
                break;
            }
        }

        if (emprestimoEncontrado == null) {
            System.out.println("Empréstimo não encontrado.");
            return;
        }

        LocalDate dataDevolucao = LocalDate.now();
        if (dataDevolucao.isAfter(emprestimoEncontrado.getDataDevolucaoPrevista())) {
            long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(
                    emprestimoEncontrado.getDataDevolucaoPrevista(), dataDevolucao);
            float valorMulta = diasAtraso * emprestimoEncontrado.getTaxaDaMulta();

            PagamentoMulta pagamento = new PagamentoMulta(
            	    0,
            	    emprestimoEncontrado.getMatriculaUsuario(),
            	    valorMulta,
            	    dataDevolucao,
            	    "Pendente"
            	);


            MultaDAO.registrarPagamento(pagamento);
            System.out.println("Multa registrada: R$" + valorMulta);
        }

        if (!obra.devolver()) {
            System.out.println("A obra já está disponível.");
            return;
        }

        atualizarObra(obra);
        EmprestimoDAO.excluirPorCodigo(codigoObra);

        System.out.println("Devolução registrada com sucesso.");
    }


    private static Emprestavel buscarObraPorCodigo(String codigoObra) {
        LivroDAO.buscarArquivo();
        for (Livro livro : LivroDAO.LISTA_DE_OBRAS) {
            if (livro.getCodigo().equals(codigoObra)) {
                return livro;
            }
        }

        RevistaDAO.buscarArquivo();
        for (Revista revista : RevistaDAO.LISTA_DE_OBRAS) {
            if (revista.getCodigo().equals(codigoObra)) {
                return revista;
            }
        }

        ArtigoDAO.buscarArquivo();
        for (Artigo artigo : ArtigoDAO.LISTA_DE_OBRAS) {
            if (artigo.getCodigo().equals(codigoObra)) {
                return artigo;
            }
        }

        return null;
    }

    private static void atualizarObra(Emprestavel obra) {
        if (obra instanceof Livro) {
            LivroDAO.excluir(((Livro) obra).getCodigo());
            LivroDAO.cadastrar((Livro) obra);
        } else if (obra instanceof Revista) {
            RevistaDAO.excluir(((Revista) obra).getCodigo());
            RevistaDAO.cadastrar((Revista) obra);
        } else if (obra instanceof Artigo) {
            ArtigoDAO.excluir(((Artigo) obra).getCodigo());
            ArtigoDAO.cadastrar((Artigo) obra);
        }
    }
}
