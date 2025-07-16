package maven.Projeto.model;

import java.time.LocalDate;

public class Emprestimo{
	private String codigoObra;
    private String matriculaUsuario;
	private String responsavel;
	private float taxaDaMulta;
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucaoPrevista;
	
	
	public Emprestimo(String codigoObra, String matriculaUsuario, int diasEmprestimo, String responsavel) {
        this.codigoObra = codigoObra;
        this.matriculaUsuario = matriculaUsuario;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(diasEmprestimo);
        this.responsavel = responsavel;
    }


	public String getCodigoObra() {
		return codigoObra;
	}


	public String getMatriculaUsuario() {
		return matriculaUsuario;
	}


	public String getResponsavel() {
		return responsavel;
	}


	public float getTaxaDaMulta() {
		return taxaDaMulta;
	}


	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}


	public LocalDate getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}


	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	
}