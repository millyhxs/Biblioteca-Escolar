package maven.Projeto.model;

public class Livro extends Obra implements Emprestavel{
	
	public Livro() {
	}
	
	public Livro(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		super(codigo, titulo, autor, anoDePublicacao, emprestado);
		this.tempoDeEmprestimo = 7;
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
        return 7;
    }
}
