package maven.Projeto.model;

public class Revista extends Obra{
	
	//Construtores
	
	public Revista() {
		
	}
	
	public Revista(String codigo, String titulo, String autor, String status, int anoDePublicacao) {
		super(codigo, titulo, autor, status, anoDePublicacao);
		this.tempoDeEmprestimo = 3;
	}
	
	//Métodos
	
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 3;
		return tempoDeEmprestimo;
	}
	
}
