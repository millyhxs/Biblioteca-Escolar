package maven.Projeto.exceptions;

/**
 * Exceção personalizada lançada quando alguma matrícula não é encontrada.
 * 
 * @author Hélder
 */
public class MatriculaNaoEncontradaException extends Exception {
	
	private static final long serialVersionUID = 7827448859875889065L;
	
	/**
     * Construtor da exceção que define a mensagem padrão indicando que a matrícula não foi encontrada.
     *
     * @param mensagem
     */
	public MatriculaNaoEncontradaException(String mensagem) {
		super("Matricula não cadastrada.");
	}
	
}
