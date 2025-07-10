package maven.Projeto.view;

import java.util.Scanner;

import maven.Projeto.dao.LivroDAO;
import maven.Projeto.dao.ArtigoDAO;
import maven.Projeto.dao.RevistaDAO;

import maven.Projeto.model.Livro;
import maven.Projeto.model.Artigo;
import maven.Projeto.model.Revista;

public class Main {
    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("O que você deseja cadastrar?\n1 - Livro\n2 - Artigo\n3 - Revista");
        int op = sc.nextInt();
        sc.nextLine(); 

        if (op == 1) {
            Livro livro = new Livro();
            System.out.println("Digite o código do livro:");
            livro.setCodigo(sc.nextLine());

            System.out.println("Título do livro:");
            livro.setTitulo(sc.nextLine());

            System.out.println("Autor do livro:");
            livro.setAutor(sc.nextLine());

            System.out.println("Ano de publicação:");
            livro.setAnoDePublicacao(sc.nextInt());

            LivroDAO.cadastrar(livro);
        }

        else if (op == 2) {
            Artigo artigo = new Artigo();
            System.out.println("Digite o código do artigo:");
            artigo.setCodigo(sc.nextLine());

            System.out.println("Título do artigo:");
            artigo.setTitulo(sc.nextLine());

            System.out.println("Autor do artigo:");
            artigo.setAutor(sc.nextLine());

            System.out.println("Ano de publicação:");
            artigo.setAnoDePublicacao(sc.nextInt());

            ArtigoDAO.cadastrar(artigo);
        }

        else if (op == 3) {
            Revista revista = new Revista();
            System.out.println("Digite o código da revista:");
            revista.setCodigo(sc.nextLine());

            System.out.println("Título da revista:");
            revista.setTitulo(sc.nextLine());

            System.out.println("Autor da revista:");
            revista.setAutor(sc.nextLine());

            System.out.println("Ano de publicação:");
            revista.setAnoDePublicacao(sc.nextInt());

            RevistaDAO.cadastrar(revista);
        }

        else {
            System.out.println("Opção inválida.");
        }

        sc.close(); 
    }
}
