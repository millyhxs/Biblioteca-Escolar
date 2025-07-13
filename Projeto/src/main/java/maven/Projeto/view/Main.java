package maven.Projeto.view;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import maven.Projeto.dao.LivroDAO;
import maven.Projeto.dao.ArtigoDAO;
import maven.Projeto.dao.RevistaDAO;
import maven.Projeto.dao.UsuarioDAO;
import maven.Projeto.model.Livro;
import maven.Projeto.model.Artigo;
import maven.Projeto.model.Revista;
import maven.Projeto.model.Usuario;

public class Main {
    public static void main(String[] args) {
    	menuCadastroUsuarios();
    }
    
    public static void menuCadastroUsuarios() {    
        Scanner sc = new Scanner(System.in);
        System.out.println("O que você deseja fazer?\n1 - Cadastrar usuário\n2 - Excluir usuário\n3 - Editar usuário");
        int op = sc.nextInt();
        sc.nextLine();

        if (op == 1) {
            Usuario usuario = new Usuario();
            System.out.println("Nome do usuário:");
            usuario.setNome(sc.nextLine());

            System.out.println("Matrícula:");
            usuario.setMatricula(sc.nextLine());

            System.out.println("Tipo de usuário:");
            usuario.setTipoDeUsuario(sc.nextLine());

            System.out.println("Telefone:");
            usuario.setTelefone(sc.nextLine());

            System.out.println("Email:");
            usuario.setEmail(sc.nextLine());

            UsuarioDAO.cadastrar(usuario);
        } 
        else if (op == 2) {
            System.out.println("Digite a matrícula do usuário:");
            String matricula = sc.nextLine();
            UsuarioDAO.excluir(matricula);
        } 
        else if (op == 3) {
            System.out.println("Digite a matrícula do usuário que deseja editar:");
            String matricula = sc.nextLine();

            Usuario novosDados = new Usuario();

            System.out.println("Novo nome:");
            novosDados.setNome(sc.nextLine());

            System.out.println("Novo tipo de usuário:");
            novosDados.setTipoDeUsuario(sc.nextLine());

            System.out.println("Novo telefone:");
            novosDados.setTelefone(sc.nextLine());

            System.out.println("Novo email:");
            novosDados.setEmail(sc.nextLine());

            UsuarioDAO.editarUsuario(matricula, novosDados);
        } 
        else {
            System.out.println("Opção inválida.");
        }

        sc.close();
    }

    public static void menuCadastroObras() {
        Scanner sc = new Scanner(System.in);
        System.out.println("O que você deseja cadastrar?\n1 - Livro\n2 - Artigo\n3 - Revista\n4 - Excluir revista\n5 - Excluir artigo\n6 - Excluir livro");
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
        else if (op == 4) {
        	System.out.println("Digite o código da revista que você quer excluir:");
        	RevistaDAO.excluir(sc.nextLine());
        }
        else if (op == 5) {
        	System.out.println("Digite o código do artigo que você quer excluir:");
        	ArtigoDAO.excluir(sc.nextLine());
        }
        else if (op == 6) {
        	System.out.println("Digite o código do livro que você quer excluir:");
        	LivroDAO.excluir(sc.nextLine());
        }
        else {
            System.out.println("Opção inválida.");
        }
        
        sc.close(); 
    }
}
