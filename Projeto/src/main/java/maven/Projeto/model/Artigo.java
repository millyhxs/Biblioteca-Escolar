package maven.Projeto.model;

public class Artigo extends Obra{
	
	//Construtores
	
	public Artigo() {
		
	}
	
	public Artigo(String codigo, String titulo, String autor, String status, int anoDePublicacao) {
		super(codigo, titulo, autor, status, anoDePublicacao);
		this.tempoDeEmprestimo = 2;
	}
	
	//Métodos
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 2;
		return tempoDeEmprestimo;
	}
	
}
