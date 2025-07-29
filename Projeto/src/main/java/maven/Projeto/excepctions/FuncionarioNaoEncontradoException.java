package maven.Projeto.excepctions;

public class FuncionarioNaoEncontradoException extends Exception {
	public FuncionarioNaoEncontradoException(String mensagem) {
        super("Funcionario não foi encontrado.");
    }
}
