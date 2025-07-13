package maven.Projeto.model;

public class Funcionario {
    private String id;
    private String nome;
    private String tipo;
    private boolean ativo;
	
    public Funcionario() {
    	
    }
    
    public Funcionario(String id, String nome, String tipo, boolean ativo) {
    	this.id = id;
    	this.nome = nome;
    	this.tipo = tipo;
    	this.ativo = ativo;
    }
    
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
    
    
}
