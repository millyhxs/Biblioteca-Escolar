package maven.Projeto.exceptions;

/**
 * Exceção personalizada lançada quando algum Funcionário não é encontrado.
 * 
 * @author Hélder
 */
public class FuncionarioNaoEncontradoException extends Exception {
	
	private static final long serialVersionUID = -640281904810762168L;
	
	/**
     * Construtor da exceção que define a mensagem padrão indicando que o Funcionário não foi encontrado.
     *
     * @param mensagem
     */
	public FuncionarioNaoEncontradoException(String mensagem) {
        super("Funcionario não cadastrado.");
    }
	
}
