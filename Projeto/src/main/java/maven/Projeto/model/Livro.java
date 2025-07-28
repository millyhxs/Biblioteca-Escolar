package maven.Projeto.model;

/**
 * Representa um Livro na biblioteca.
 * Um livro herda {@link Obra}.
 * Pode ser emprestado e devolvido, seguindo a regra de empréstimo de 7 dias.
 * 
 * Esta classe implementa a interface {@link Emprestavel}, permitindo controle sobre empréstimos.
 * 
 * @author
 */
public class Livro extends Obra implements Emprestavel{
	
	/**
     * Construtor que inicializa os dados de um livro.
     * O tempo de empréstimo é fixo em 7 dias.
     * 
     * @param codigo Código identificador do livro
     * @param titulo Título do livro
     * @param autor Autor do livro
     * @param anoDePublicacao Ano de publicação do livro
     * @param emprestado Estado inicial do livro
     */
	public Livro(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		super(codigo, titulo, autor, anoDePublicacao, emprestado);
		this.setTempoDeEmprestimo(7);
	}
	
	/**
     * Construtor vazio. Utilizado para leitura de dados via GSON ou frameworks.
     * O tempo de empréstimo é definido como 7 dias.
     */
	public Livro() {
		this.setTempoDeEmprestimo(7);
	}
	
	/**
     * Empresta o livro, se ele estiver disponível.
     * 
     * @return {@code true} se o livro foi emprestado com sucesso; {@code false} se já estava emprestado.
     */
	@Override
	public boolean emprestar() {
        if (!emprestado) {
            emprestado = true;
            return true;
        }
        return false;
    }
	
	/**
     * Devolve o livro, se ele estiver emprestado.
     * 
     * @return {@code true} se a devolução foi feita com sucesso; {@code false} se o livro já estava disponível.
     */
	@Override
	public boolean devolver() {
        if (emprestado) {
            emprestado = false;
            return true;
        }
        return false;
    }
	
	/**
     * Devolve o livro, se ele estiver emprestado.
     * 
     * @return {@code true} se a devolução foi feita com sucesso; {@code false} se o livro já estava disponível.
     */
	@Override
	public boolean isEmprestado() {
        return emprestado;
    }
	
	/**
     * Retorna o tempo máximo de empréstimo para o livro.
     * 
     * @return Tempo de empréstimo, em dias (7 dias para livros).
     */
    @Override
    public int getTempoEmprestimo() {
        return 7;
    }
    
}
