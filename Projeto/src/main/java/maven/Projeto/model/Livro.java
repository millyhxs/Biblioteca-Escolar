package maven.Projeto.model;

public class Livro extends Obra{
	
	public Livro() {
		this.tempoDeEmprestimo = 7;
	}
	
	public Livro(String codigo, String titulo, String autor, int anoDePublicacao) {
		super(codigo, titulo, autor, anoDePublicacao);
		this.tempoDeEmprestimo = 7;
	}
	
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 7;
		return tempoDeEmprestimo;
		
	}
	
}
