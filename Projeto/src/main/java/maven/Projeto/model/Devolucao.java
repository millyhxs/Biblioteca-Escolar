package maven.Projeto.model;

import java.time.LocalDate;

public class Devolucao {
	private String codigoObra;
    private String matriculaUsuario;
    private LocalDate dataDevolucao;
 
    public Devolucao(String codigoObra, String matriculaUsuario, LocalDate dataDevolucao) {
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
	
	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}
	
	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}       
   
}

