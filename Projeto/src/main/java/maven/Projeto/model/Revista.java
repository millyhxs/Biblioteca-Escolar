package maven.Projeto.model;

public class Revista extends Obra implements Emprestavel{
	
	public Revista() {
		this.tempoDeEmprestimo = 3;
	}
	
	public Revista(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		super(codigo, titulo, autor, anoDePublicacao, emprestado);
		this.tempoDeEmprestimo = 3;
	}
	
	@Override
	public boolean emprestar() {
        if (!emprestado) {
            emprestado = true;
            return true;
        }
        return false;
    }
	@Override
	public boolean devolver() {
        if (emprestado) {
            emprestado = false;
            return true;
        }
        return false;
    }
	@Override
	public boolean isEmprestado() {
        return emprestado;
    }

    @Override
    public int getTempoEmprestimo() {
        return 3;
    }
}
