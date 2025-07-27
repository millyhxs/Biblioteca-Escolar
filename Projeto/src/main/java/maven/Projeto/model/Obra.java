package maven.Projeto.model;

/**
 * Classe abstrata que representa uma Obra em uma biblioteca.
 * Pode ser uma subclasse como Livro, Revista ou Artigo.
 * 
 * Armazena informações comuns a todas as obras, como código, título, autor, ano de publicação,
 * tempo de empréstimo permitido e estado de empréstimo.
 * 
 * @author 
 */
public abstract class Obra {
	
	/**
     * codigo | Código da obra.
     * titulo | Título da obra.
     * autor | Autor da obra.
     * anoDePublicacao | Ano da publicação.
     * emprestado | Estado inicial de empréstimo.
     */
	protected String codigo;
    protected String titulo;
    protected String autor;
    protected String anoDePublicacao;
    protected int tempoDeEmprestimo;
    protected boolean emprestado;
    
    /**
     * Construtor que inicializa os dados principais da obra.
     * 
     * O campo {@code emprestado} é iniciado como {@code false}.
     */
    public Obra(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		this.codigo = codigo;
		this.titulo = titulo;
		this.autor = autor;
		this.anoDePublicacao = anoDePublicacao;
		this.emprestado = false;
		
	}
    
    /**
     * Construtor vazio.
     */
    public Obra() {
    	
    }
    
    /**
     * Método abstrato que define o tempo máximo de empréstimo para a obra.
     * Deve ser implementado nas subclasses (Livro, Revista e Artigo).
     * 
     * @return Tempo de empréstimo permitido, em dias.
     */
	public abstract int getTempoEmprestimo();
	
	// Getters e Setters
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String código) {
		this.codigo = código;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getAnoDePublicacao() {
		return anoDePublicacao;
	}
	
	public void setAnoDePublicacao(String anoDePublicacao) {
		this.anoDePublicacao = anoDePublicacao;
	}
	
	public boolean isEmprestado() {
		return emprestado;
	}
	
	public void setEmprestado(boolean emprestado) {
		this.emprestado = emprestado;
	}
	
}