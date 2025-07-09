package maven.Projeto.model;

public abstract class Obra {
	//Atributos de obra
	
	protected String código;
    protected String titulo;
    protected String autor;
    protected String Status;
    protected int anoDePublicacao;
    protected int tempoDeEmprestimo;
    protected boolean emprestado;
    
    //Construtores
    
    public Obra() {
    	
    }
    
    public Obra(String código, String titulo, String autor, String status, int anoDePublicacao) {
		this.código = código;
		this.titulo = titulo;
		this.autor = autor;
		Status = status;
		this.anoDePublicacao = anoDePublicacao;
		this.emprestado = false;

	}
    
    //Métodos
    
	public abstract int getTempoEmprestimo();
	
	
	// Get's & Set's
	
	public String getCódigo() {
		return código;
	}
	
	public void setCódigo(String código) {
		this.código = código;
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
	
	public String getStatus() {
		return Status;
	}
	
	public void setStatus(String status) {
		Status = status;
	}
	
	public int getAnoDePublicacao() {
		return anoDePublicacao;
	}
	
	public void setAnoDePublicacao(int anoDePublicacao) {
		this.anoDePublicacao = anoDePublicacao;
	}
}