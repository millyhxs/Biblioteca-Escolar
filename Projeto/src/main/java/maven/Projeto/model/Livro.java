package maven.Projeto.model;

public class Livro extends Obra{
	
	//Construtores
	
	public Livro() {
		
	}
	
	public Livro(String código, String titulo, String autor, String status, int anoDePublicacao) {
		super(código, titulo, autor, status, anoDePublicacao);
	}
	
	//Métodos
	
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 2;
		return tempoDeEmprestimo;
		
	}
	
}
