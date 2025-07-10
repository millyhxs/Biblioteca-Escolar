package maven.Projeto.view;

import java.util.Scanner;

//Bibliotecas

// imports

import maven.Projeto.dao.CadastroDeObras;
import maven.Projeto.model.Livro;

public class Main {
	public static void main(String[] args) {
		menu();
		
		
	}
	
	public static void menu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Oque voce deseja cadastrar?\n1 - Livro\n2 - Artigo\n3 - Revista");
		int op = sc.nextInt();
		sc.nextLine();
		if (op == 1) {
			Livro livro = new Livro();
			System.out.println("Digite o codigo do livro: ");
			livro.setCodigo(sc.nextLine());
			
			System.out.println("Nome do livro: ");
			livro.setTitulo(sc.nextLine());
			
			System.out.println("Autor do livro: ");
			livro.setAutor(sc.nextLine());
			
			System.out.println("Ano de publicação");
			livro.setAnoDePublicacao(sc.nextInt());
			
			CadastroDeObras.cadastrar(livro);
		}
	}
}
