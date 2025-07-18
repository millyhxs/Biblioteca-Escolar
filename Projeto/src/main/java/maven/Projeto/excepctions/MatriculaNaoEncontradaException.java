package maven.Projeto.excepctions;

public class MatriculaNaoEncontradaException extends Exception {
	public MatriculaNaoEncontradaException(String mensagem) {
		super("Matricula não cadastrada.");
	}
	
}
