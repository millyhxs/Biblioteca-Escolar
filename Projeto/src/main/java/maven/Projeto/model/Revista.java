package maven.Projeto.model;

public class Revista extends Obra{
	
	public Revista() {
		this.tempoDeEmprestimo = 3;
	}
	
	public Revista(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		super(codigo, titulo, autor, anoDePublicacao, emprestado);
		this.tempoDeEmprestimo = 3;
	}
	
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 3;
		return tempoDeEmprestimo;
	}
	
}
