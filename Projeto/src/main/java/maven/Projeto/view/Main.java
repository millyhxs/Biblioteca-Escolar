package maven.Projeto.view;

import java.io.FileWriter;
import java.io.IOException;

//Bibliotecas

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// imports

import maven.Projeto.model.Livro;

public class Main {
	public static void main(String[] args) {
		
		
		Livro teste = new Livro("abcm", "Caminho dos reis", "Brandon Sanderson", "Disponível", 2005);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String json = gson.toJson(teste);
		
		System.out.println(json);
		
		try (FileWriter listaDeLivros = new FileWriter("listaDeLivros.json")) {
			gson.toJson(teste, listaDeLivros);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
