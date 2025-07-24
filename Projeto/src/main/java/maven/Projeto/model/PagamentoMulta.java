package maven.Projeto.model;

import java.time.LocalDate;

public class PagamentoMulta {
    private int id;
    private String matriculaUsuario;
    private float valor;
    private LocalDate dataDePagamento;
    private String metodoDoPagamento;

    public PagamentoMulta(int id, String matriculaUsuario, float valor, LocalDate dataDePagamento, String metodoDoPagamento) {
        this.id = id;
        this.matriculaUsuario = matriculaUsuario;
        this.valor = valor;
        this.dataDePagamento = dataDePagamento;
        this.metodoDoPagamento = metodoDoPagamento;
    }
    
    
    public PagamentoMulta(String matriculaUsuario, float valor, LocalDate dataDePagamento, String status) {
        this.matriculaUsuario = matriculaUsuario;
        this.valor = valor;
        this.dataDePagamento = dataDePagamento;
        this.metodoDoPagamento = status;
    }
    
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
