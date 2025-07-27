package maven.Projeto.model;

public class Devolucao {
	private String codigoObra;
    private String matriculaUsuario;
    private String dataDevolucao;
 
    public Devolucao(String codigoObra, String matriculaUsuario, String dataDevolucao) {
        this.codigoObra = codigoObra;
        this.matriculaUsuario = matriculaUsuario;
        this.dataDevolucao = dataDevolucao;
        
    }
    
    public Devolucao() {
    	
    }
    
    // Getters e Setters
    
	public String getCodigoObra() {
		return codigoObra;
	}
	
	public void setCodigoObra(String codigoObra) {
		this.codigoObra = codigoObra;
	}
	
	public String getMatriculaUsuario() {
		return matriculaUsuario;
	}
	
	public void setMatriculaUsuario(String matriculaUsuario) {
		this.matriculaUsuario = matriculaUsuario;
	}
	
	public String getDataDevolucao() {
		return dataDevolucao;
	}
	
	public void setDataDevolucao(String dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}       
   
}

