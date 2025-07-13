package maven.Projeto.model;

public abstract class Obra {
	
	protected String codigo;
    protected String titulo;
    protected String autor;
    protected int anoDePublicacao;
    protected int tempoDeEmprestimo;
    protected boolean emprestado;
    
    
    public Obra() {
    	
    }
    
    public Obra(String codigo, String titulo, String autor, int anoDePublicacao) {
		this.codigo = codigo;
		this.titulo = titulo;
		this.autor = autor;
		this.anoDePublicacao = anoDePublicacao;
		this.emprestado = false;
		
	}
    
	public abstract int getTempoEmprestimo();
	
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
	
	public int getAnoDePublicacao() {
		return anoDePublicacao;
	}
	
	public void setAnoDePublicacao(int anoDePublicacao) {
		this.anoDePublicacao = anoDePublicacao;
	}
	
}