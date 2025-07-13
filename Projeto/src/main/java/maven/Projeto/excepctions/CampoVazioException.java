package maven.Projeto.excepctions;

public class CampoVazioException extends Exception {
	public CampoVazioException(String mensagem) {
		super("O campo não pode estar vazio");
	}
}
