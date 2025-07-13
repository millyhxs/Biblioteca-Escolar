package maven.Projeto.model;

public class Revista extends Obra{
	
	public Revista() {
		this.tempoDeEmprestimo = 3;
	}
	
	public Revista(String codigo, String titulo, String autor, int anoDePublicacao) {
		super(codigo, titulo, autor, anoDePublicacao);
		this.tempoDeEmprestimo = 3;
	}
	
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 3;
		return tempoDeEmprestimo;
	}
	
}
