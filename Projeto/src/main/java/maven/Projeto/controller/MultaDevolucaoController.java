package maven.Projeto.controller;

import java.util.List;

import maven.Projeto.dao.MultaDAO;
import maven.Projeto.model.PagamentoMulta;

public class MultaDevolucaoController {
	private MultaDAO multaDAO = new MultaDAO();
	
	public void registroDePagamento(PagamentoMulta pagamento) {
		multaDAO.registrarPagamento(pagamento);
	}
	
	public List<PagamentoMulta> listarTodosPagamentos() {
        return multaDAO.getTodosPagamentos();
    }
	
}
