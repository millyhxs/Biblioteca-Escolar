package maven.Projeto.model;

import java.time.LocalDate;

public class PagamentoMulta {
    private int id;
    private String matriculaUsuario;
    private float valor;
    private LocalDate data;
    private String status;

    public PagamentoMulta(int id, String matriculaUsuario, float valor, LocalDate data, String status) {
        this.id = id;
        this.matriculaUsuario = matriculaUsuario;
        this.valor = valor;
        this.data = data;
        this.status = status;
    }

    
    public PagamentoMulta(String matriculaUsuario, float valor, LocalDate data, String status) {
        this.matriculaUsuario = matriculaUsuario;
        this.valor = valor;
        this.data = data;
        this.status = status;
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

    public LocalDate getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }
}
