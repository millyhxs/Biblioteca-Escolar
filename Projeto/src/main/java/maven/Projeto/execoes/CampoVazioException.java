package maven.Projeto.execoes;

public class CampoVazioException extends Exception {
	public CampoVazioException(String mensagem) {
		super("O campo não pode estar vazio");
	}
}
