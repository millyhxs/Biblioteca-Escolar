package maven.Projeto.model;

/**
 * Interface que define o contrato para obras que podem ser emprestadas.
 * 
 * Qualquer classe que implemente esta interface deve fornecer mecanismos para controlar
 * o estado de empréstimo de um item, como realizar e devolver o empréstimo.
 * 
 * @author
 */
public interface Emprestavel {
	
	/**
     * Realiza o empréstimo do item.
     * 
     * @return true se o item foi emprestado com sucesso; false se o item já estava emprestado.
     */
	boolean emprestar();
	
	/**
     * Realiza a devolução do item.
     * 
     * @return true se a devolução foi realizada com sucesso; false se o item não estava emprestado.
     */
	boolean devolver();
	
	/**
     * Verifica se o item está atualmente emprestado.
     * 
     * @return true se o item está emprestado; false caso contrário.
     */
	boolean isEmprestado();
	
}
