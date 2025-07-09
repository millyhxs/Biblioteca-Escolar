package maven.Projeto.model;

public class Livro extends Obra{
	
	//Construtores
	
	public Livro() {
		
	}
	
	public Livro(String código, String titulo, String autor, String status, int anoDePublicacao) {
		super(código, titulo, autor, status, anoDePublicacao);
		this.tempoDeEmprestimo = 7;
	}
	
	//Métodos
	
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 7;
		return tempoDeEmprestimo;
		
	}
	
}
