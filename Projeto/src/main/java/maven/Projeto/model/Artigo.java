package maven.Projeto.model;

public class Artigo extends Obra{
	
	//Construtores
	
	
	public Artigo(String código, String titulo, String autor, String status, int anoDePublicacao) {
		super(código, titulo, autor, status, anoDePublicacao);
	}
	
	//Métodos
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 2;
		return tempoDeEmprestimo;
	}
	
}
