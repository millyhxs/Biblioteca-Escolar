package maven.Projeto.model;

public class Artigo extends Obra{
	
	//Construtores
	
	public Artigo() {
		this.tempoDeEmprestimo = 2;
	}
	
	public Artigo(String codigo, String titulo, String autor, int anoDePublicacao) {
		super(codigo, titulo, autor, anoDePublicacao);
		this.tempoDeEmprestimo = 2;
	}
	
	//Métodos
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 2;
		return tempoDeEmprestimo;
	}
	
}
