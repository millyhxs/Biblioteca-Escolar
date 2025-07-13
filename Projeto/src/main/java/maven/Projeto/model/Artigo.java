package maven.Projeto.model;

public class Artigo extends Obra{

	
	public Artigo() {
		this.tempoDeEmprestimo = 2;
	}
	
	public Artigo(String codigo, String titulo, String autor, int anoDePublicacao) {
		super(codigo, titulo, autor, anoDePublicacao);
		this.tempoDeEmprestimo = 2;
	}
	
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 2;
		return tempoDeEmprestimo;
	}
	
}
