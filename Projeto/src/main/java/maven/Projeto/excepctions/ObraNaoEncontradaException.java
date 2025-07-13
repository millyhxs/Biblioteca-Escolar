package maven.Projeto.excepctions;

public class ObraNaoEncontradaException extends Exception {
	public ObraNaoEncontradaException(String mensagem) {
		super("Não foi possível encontrar essa obra.");
	}
	
}
