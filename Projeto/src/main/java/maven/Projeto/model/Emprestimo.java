package maven.Projeto.model;

import java.time.LocalDate;

/**
 * Representa um empréstimo de uma obra no sistema da biblioteca.
 * 
 * Armazena dados como código da obra, matrícula do usuário, datas do empréstimo e devolução, 
 * responsável e taxa de multa por atraso.
 * 
 * @author
 */
public class Emprestimo{
	
	/**
     * codigoObra | Código da Obra.
     * matriculaUsuario | Matricula do Usuário que pegou emprestado.
     * responsavel | Nome do funcionário responsável pelo emprestimo.
     * taxaDaMulta | Taxa da multa de atraso.
     * dataEmprestimo | Data em que o emprestimo foi realizado.
     * dataDevolucaoPrevista | Data de entrega prevista da Obra.
     */
	private String codigoObra;
    private String matriculaUsuario;
	private String responsavel;
	private float taxaDaMulta;
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucaoPrevista;
	
	/**
     * Cria um novo empréstimo com base nas informações da obra e do usuário.
     * A data do empréstimo é definida como a data atual e a data de devolução é calculada com base no prazo.
     * A taxa padrão por dia de multa é definida como 2.5.
     * 
     * @param codigoObra Código da obra emprestada
     * @param matriculaUsuario Matrícula do leitor que está emprestando
     * @param diasEmprestimo Quantidade de dias permitidos para devolução
     * @param responsavel Nome do funcionário responsável pelo empréstimo
     */
	public Emprestimo(String codigoObra, String matriculaUsuario, int diasEmprestimo, String responsavel) {
        this.codigoObra = codigoObra;
        this.matriculaUsuario = matriculaUsuario;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(diasEmprestimo);
        this.responsavel = responsavel;
        this.taxaDaMulta = 2.5f;
    }
	
	/**
     * Construtor utilizado quando não há informação de obra ou usuário ainda.
     * Define a data do empréstimo como a data atual e calcula a data de devolução.
     * 
     * @param diasEmprestimo Número de dias para devolução
     */
	public Emprestimo(int diasEmprestimo) {
		this.dataEmprestimo = LocalDate.now();
        this.dataDevolucaoPrevista = dataEmprestimo.plusDays(diasEmprestimo);
        this.taxaDaMulta = 2.5f;
	}
	
	// Getters and Setters
	
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