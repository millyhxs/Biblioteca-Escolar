package maven.Projeto.excepctions;

public class ValorNuloException extends Exception{
	public ValorNuloException(String mensagem) {
		super("Valor nulo");
	}
	
}
