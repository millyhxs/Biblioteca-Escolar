package maven.Projeto.model;

/**
 * Representa um Artigo na biblioteca.
 * Um artigo é um tipo de {@link Obra} que pode ser emprestado e devolvido,
 * com um tempo de empréstimo máximo de 2 dias.
 * 
 * Esta classe implementa a interface {@link Emprestavel}, permitindo controle sobre empréstimos.
 * 
 * @author
 */
public class Artigo extends Obra implements Emprestavel{
	
	/**
     * Construtor que inicializa os dados de um artigo.
     * O tempo de empréstimo é fixo em 2 dias.
     *
     * @param codigo Código identificador do artigo
     * @param titulo Título do artigo
     * @param autor Autor do artigo
     * @param anoDePublicacao Ano de publicação do artigo
     * @param emprestado Estado inicial do artigo (geralmente {@code false})
     */
	public Artigo(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		super(codigo, titulo, autor, anoDePublicacao, emprestado);
		this.setTempoDeEmprestimo(2);
	}
	
	/**
     * Construtor vazio. Utilizado para leitura de dados via GSON ou frameworks.
     * O tempo de empréstimo é definido como 2 dias.
     */
	public Artigo() {
		this.setTempoDeEmprestimo(2);
	}
	
	/**
     * Empresta o artigo, se ele estiver disponível.
     *
     * @return {@code true} se o artigo foi emprestado com sucesso; {@code false} se já estava emprestado.
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
     * Devolve o artigo, se ele estiver emprestado.
     *
     * @return {@code true} se a devolução foi feita com sucesso; {@code false} se o artigo já estava disponível.
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
     * Verifica se o artigo está atualmente emprestado.
     *
     * @return {@code true} se está emprestado; {@code false} se está disponível.
     */
	@Override
	public boolean isEmprestado() {
        return emprestado;
    }
	
	/**
     * Retorna o tempo máximo de empréstimo para o artigo.
     *
     * @return Tempo de empréstimo, em dias (2 dias para artigos).
     */
    @Override
    public int getTempoEmprestimo() {
        return 2;
    }
    
}
