package maven.Projeto.model;

public class Emprestimo implements Emprestavel{
	private Obra obra;
	private Leitor leitor;
	private String responsavel;
	private float taxaDaMulta;
	private int dataDeRetirada;
	private int dataDeEntrega;
	
	
	public Emprestimo() {
		
	}

	@Override
	public boolean emprestar() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean devolver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmprestado() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
