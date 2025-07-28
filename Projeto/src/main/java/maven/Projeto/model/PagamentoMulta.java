package maven.Projeto.model;

import java.time.LocalDate;

/**
 * Representa um pagamento de multa realizado por um usuário da biblioteca.
 * 
 * Esta classe armazena informações sobre o pagamento, como o valor, data,
 * método utilizado e a matrícula do usuário responsável.
 * 
 * @author 
 */
public class PagamentoMulta {
    
	/**
     * id | ID único do pagamento.
     * matriculaUsuario | Matricula do Usuário que pegou emprestado.
     * valor | Valor da multa à ser pago.
     * dataDePagamento | Data que o pagamento foi realizado.
     * metodoDoPagamento | Método utilizado para fazer o pagamento da multa.
     */
	private int id;
    private String matriculaUsuario;
    private float valor;
    private LocalDate dataDePagamento;
    private String metodoDoPagamento;
    
    /**
     * Cria uma nova multa com base nos atributos.
     * 
     * @param id ID do pagamento.
     * @param matriculaUsuario Matrícula do usuário que pagou a multa.
     * @param valor Valor da multa.
     * @param dataDePagamento Data do pagamento.
     * @param metodoDoPagamento Método utilizado para pagamento.
     */
    public PagamentoMulta(int id, String matriculaUsuario, float valor, LocalDate dataDePagamento, String metodoDoPagamento) {
        this.id = id;
        this.matriculaUsuario = matriculaUsuario;
        this.valor = valor;
        this.dataDePagamento = dataDePagamento;
        this.metodoDoPagamento = metodoDoPagamento;
    }
    
    /**
     * Construtor alternativo sem o ID (ID gerado posteriormente).
     * 
     * @param matriculaUsuario Matrícula do usuário.
     * @param valor Valor da multa.
     * @param dataDePagamento Data de pagamento.
     * @param status Método utilizado para pagamento.
     */
    public PagamentoMulta(String matriculaUsuario, float valor, LocalDate dataDePagamento, String status) {
        this.matriculaUsuario = matriculaUsuario;
        this.valor = valor;
        this.dataDePagamento = dataDePagamento;
        this.metodoDoPagamento = status;
    }
    
    // Getters and Setters
    
	public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getMatriculaUsuario() {
        return matriculaUsuario;
    }
    
    public float getValor() {
        return valor;
    }
    
    public void setValor(float valor) {
    	this.valor = valor;
    }
    
    public LocalDate getDataDePagamento() {
        return dataDePagamento;
    }
    
    public String getMetodoDoPagamento() {
        return metodoDoPagamento;
    }
    
}
