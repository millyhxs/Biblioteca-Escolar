package maven.Projeto.model;

import java.time.LocalDate;

public class Devolucao {
	private String codigoObra;
    private String matriculaUsuario;
    private LocalDate dataDevolucao;
    private LocalDate dataEmprestimo;
 
    public Devolucao(String codigoObra, String matriculaUsuario, LocalDate localDate, LocalDate localDate2) {
        this.codigoObra = codigoObra;
        this.matriculaUsuario = matriculaUsuario;
        this.dataDevolucao = localDate;
        this.dataEmprestimo = localDate2;
        
    }
    
    public Devolucao(String string, String string2, String dataFormatada) {
    	
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

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}
	

   
}

