package maven.Projeto.model;

import java.time.LocalDate;

/**
 * Representa uma devolução de uma obra no sistema da biblioteca.
 * 
 * Armazena dados como código da obra, matrícula do usuário, datas de devolução e data de emprestimo.
 * 
 * @author Hélder
 */
public class Devolucao {
	
	/**
     * codigoObra | Código da Obra.
     * matriculaUsuario | Matricula do Usuário que pegou emprestado.
     * dataDevolucao | Data de entrega da Obra.
     * dataEmprestimo | Data em que o emprestimo foi realizado.
     */
	private String codigoObra;
    private String matriculaUsuario;
    private LocalDate dataDevolucao;
    private LocalDate dataEmprestimo;
 
    /**
     * COnstrutor que inicializa os dados de uma devolução.
     * 
     * @param codigoObra Código da obra emprestada
     * @param matriculaUsuario Matrícula do leitor que está emprestando
     * @param dataDeEmprestimo Data em que o emprestimo foi realizado
     * @param dataDeDevolucao dataDeDevolucao
     */
    public Devolucao(String codigoObra, String matriculaUsuario, LocalDate dataDeEmprestimo, LocalDate dataDeDevolucao) {
        this.codigoObra = codigoObra;
        this.matriculaUsuario = matriculaUsuario;
        this.dataEmprestimo = dataDeEmprestimo;
        this.dataDevolucao = dataDeDevolucao;
        
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

