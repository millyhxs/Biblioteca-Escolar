package maven.Projeto.excepctions;

/**
 * Exceção personalizada lançada quando algum campo obrigatório não está preenchido.
 * 
 * @author Hélder
 */
public class CampoVazioException extends Exception {
	
	private static final long serialVersionUID = 8773375851895728728L;
	
	/**
     * Construtor da exceção que define a mensagem padrão indicando que todos os campos devem ser preenchidos.
     *
     * @param mensagem
     */
	public CampoVazioException(String mensagem) {
		super("Os campos devem estar todos preenchidos");
	}
	
}
