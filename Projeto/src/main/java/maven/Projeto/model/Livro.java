package maven.Projeto.model;

public class Livro extends Obra{
	
	public Livro() {
		this.tempoDeEmprestimo = 7;
	}
	
	public Livro(String codigo, String titulo, String autor, String anoDePublicacao, boolean emprestado) {
		super(codigo, titulo, autor, anoDePublicacao, emprestado);
		this.tempoDeEmprestimo = 7;
	}
	
	@Override
	public int getTempoEmprestimo() {
		tempoDeEmprestimo = 7;
		return tempoDeEmprestimo;
		
	}
	
}
