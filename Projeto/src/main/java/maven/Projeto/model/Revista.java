package maven.Projeto.model;

/**
 * Representa uma Revista na biblioteca.
 * Uma revista é um tipo de {@link Obra} que pode ser emprestado e devolvido,
 * com um tempo de empréstimo máximo de 3 dias.
 * 
 * Esta classe implementa a interface {@link Emprestavel}, permitindo controle sobre empréstimos.
 * 
 * @author
 */
public class Revista extends Obra implements Emprestavel{
	
	/**
     * Construtor que inicializa os dados de uma revista.
     * O tempo de empréstimo é fixo em 3 dias.
     *
     * @param codigo Código identificador da revista
     * @param titulo Título da revista
     * @param autor Autor da revista
     * @param anoDePublicacao Ano de publicação da revista
     * @param emprestado Estado inicial da revista (geralmente {@code false})
     */
	public Revista(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		super(codigo, titulo, autor, anoDePublicacao, emprestado);
		this.tempoDeEmprestimo = 3;
	}
	
	/**
     * Construtor vazio. Utilizado para leitura de dados via GSON ou frameworks.
     * O tempo de empréstimo é definido como 3 dias.
     */
	public Revista() {
		this.tempoDeEmprestimo = 3;
	}
	
	/**
     * Empresta a revista, se ela estiver disponível.
     *
     * @return {@code true} se a revista foi emprestada com sucesso; {@code false} se já estava emprestada.
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
     * Devolve a revista, se ela estiver emprestada.
     *
     * @return {@code true} se a devolução foi feita com sucesso; {@code false} se a revista já estava disponível.
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
     * Verifica se a revista está atualmente emprestada.
     *
     * @return {@code true} se está emprestada; {@code false} se está disponível.
     */
	@Override
	public boolean isEmprestado() {
        return emprestado;
    }
	
	/**
     * Retorna o tempo máximo de empréstimo para a revista.
     *
     * @return Tempo de empréstimo, em dias (3 dias para revistas).
     */
    @Override
    public int getTempoEmprestimo() {
        return 3;
    }
    
}
