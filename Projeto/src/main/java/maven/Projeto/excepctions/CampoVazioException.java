package maven.Projeto.excepctions;

public class CampoVazioException extends Exception {
	public CampoVazioException(String mensagem) {
		super("Os campos devem estar todos preenchidos");
	}
}
