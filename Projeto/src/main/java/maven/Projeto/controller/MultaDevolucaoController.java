package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.MultaDAO;
import maven.Projeto.model.PagamentoMulta;

/**
 * Classe responsável por controlar as operações relacionadas ao pagamento de multas.
 * Atua como intermediária entre a interface (View) e o acesso aos dados (DAO).
 *
 * @author Millena
 */
public class MultaDevolucaoController {
	private MultaDAO multaDAO = new MultaDAO();
	
	/**
     * Registra um pagamento de multa.
     * 
     * @param pagamento Pagamento a ser registrado
     */
	public void registroDePagamento(PagamentoMulta pagamento) {
		multaDAO.registrarPagamento(pagamento);
	}
	
	/**
     * Retorna todos os pagamentos de multa registrados.
     */
	public List<PagamentoMulta> listarTodosPagamentos() {
        return multaDAO.getTodosPagamentos();
    }
	
}
