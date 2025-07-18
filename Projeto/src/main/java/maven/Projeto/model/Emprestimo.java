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
        this.taxaDaMulta = 2.5f;
    }
	
	public Emprestimo(int diasEmprestimo) {
		this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(diasEmprestimo);
        this.taxaDaMulta = 2.5f;
	}
	
	public void setCodigoObra(String codigoObra) {
		this.codigoObra = codigoObra;
	}

	public void setMatriculaUsuario(String matriculaUsuario) {
		this.matriculaUsuario = matriculaUsuario;
	}

	public void setTaxaDaMulta(float taxaDaMulta) {
		this.taxaDaMulta = taxaDaMulta;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
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