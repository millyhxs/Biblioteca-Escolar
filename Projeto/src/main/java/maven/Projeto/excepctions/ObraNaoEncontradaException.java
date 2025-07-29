package maven.Projeto.excepctions;

/**
 * Exceção personalizada lançada quando alguma Obra não é encontrada.
 * 
 * @author Hélder
 */
public class ObraNaoEncontradaException extends Exception {
	
	private static final long serialVersionUID = -7688086712478081150L;
	
	/**
     * Construtor da exceção que define a mensagem padrão indicando que a Obra não foi encontrada.
     *
     * @param mensagem
     */
	public ObraNaoEncontradaException(String mensagem) {
		super("Não foi possível encontrar essa obra.");
	}
	
}
