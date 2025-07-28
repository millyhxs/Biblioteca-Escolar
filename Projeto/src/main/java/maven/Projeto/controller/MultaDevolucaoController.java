package maven.Projeto.controller;

import maven.Projeto.dao.DevolucaoDAO;
import maven.Projeto.dao.MultaDAO;
import maven.Projeto.model.Devolucao;
import maven.Projeto.model.PagamentoMulta;

public class MultaDevolucaoController {
	MultaDAO multaDAO = new MultaDAO();
	DevolucaoDAO devolucaoDAO = new DevolucaoDAO();
	
	public void registroDePagamento(PagamentoMulta pagamento) {
		multaDAO.registrarPagamento(pagamento);
	}
	
	public void registroDeDevolucao(Devolucao devolucao) {
		devolucaoDAO.registrarDevolucao(devolucao);
	}
	
}
