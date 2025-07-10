package maven.Projeto.execoes;

public class ObraNaoEncontradaException extends Exception {
	public ObraNaoEncontradaException(String mensagem) {
		super("Não foi possível encontrar essa obra.");
	}
	
}
