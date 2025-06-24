package maven.Projeto.view;

//Bibliotecas

import com.google.gson.Gson;

// imports

import maven.Projeto.model.Livro;

public class Main {
	public static void main(String[] args) {
		Livro teste = new Livro("abcm", "Caminho dos reis", "Brandon Sanderson", "Disponível", 2005);
		Gson gson = new Gson();		
	 
		String json = gson.toJson(teste);
		
		System.out.println(json);
	}
}
