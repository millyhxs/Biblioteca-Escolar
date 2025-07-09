package maven.Projeto.dao;

import java.io.FileWriter;
import java.io.IOException;

//Bibliotecas

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Imports

import maven.Projeto.model.Obra;

public abstract class CadastroDeObras extends Obra {
	// Classe responsável para cadastrar as obras
	
	private static final String CAMINHO = "listaDeLivros.json";
	
	public static void cadastrar(Obra teste) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();	
		
		try (FileWriter listaDeLivros = new FileWriter(CAMINHO)) {
			gson.toJson(teste, listaDeLivros);	
			System.out.println("Cadastro concluido!");
		} catch (IOException e) {
			System.out.println("Ocorreu um erro de leitura ou escrita!");
		}
	}
	
}
