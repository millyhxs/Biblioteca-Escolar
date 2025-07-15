package maven.Projeto.model;

public class Artigo extends Obra{

	
	public Artigo() {
		this.tempoDeEmprestimo = 2;
	}
	
	public Artigo(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		super(codigo, titulo, autor, anoDePublicacao, emprestado);
		this.tempoDeEmprestimo = 2;
	}
	
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 2;
		return tempoDeEmprestimo;
	}
	
}
