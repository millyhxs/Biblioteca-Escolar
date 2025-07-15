package maven.Projeto.model;

public class Artigo extends Obra implements Emprestavel{

	
	public Artigo() {
		this.tempoDeEmprestimo = 2;
	}
	
	public Artigo(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		super(codigo, titulo, autor, anoDePublicacao, emprestado);
		this.tempoDeEmprestimo = 2;
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
        return 2;
    }
}
