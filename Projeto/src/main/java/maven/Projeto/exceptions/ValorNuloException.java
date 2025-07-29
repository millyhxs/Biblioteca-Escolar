package maven.Projeto.exceptions;

/**
 * Exceção personalizada lançada quando algum objeto está nulo.
 * 
 * @author Hélder
 */
public class ValorNuloException extends Exception{
	
	private static final long serialVersionUID = -5656538509180707470L;
	
	/**
     * Construtor da exceção que define a mensagem padrão indicando que algum objeto está nulo.
     *
     * @param mensagem
     */
	public ValorNuloException(String mensagem) {
		super("Objeto com valor nulo");
	}
	
}
