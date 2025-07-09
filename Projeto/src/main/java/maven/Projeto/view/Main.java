package maven.Projeto.view;

//Bibliotecas

// imports

import maven.Projeto.dao.CadastroDeObras;
import maven.Projeto.model.Livro;
import maven.Projeto.model.Obra;

public class Main {
	public static void main(String[] args) {
		Obra teste = new Livro("0000", "Caminho dos reis", "Brandon Sanderson", "Disponível", 2005);
		CadastroDeObras.cadastrar(teste);
	}
}
