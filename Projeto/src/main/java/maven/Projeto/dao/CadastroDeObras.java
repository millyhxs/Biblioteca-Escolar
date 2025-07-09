package maven.Projeto.dao;

import java.io.FileReader;

//Bibliotecas

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Imports

import maven.Projeto.model.Obra;

public abstract class CadastroDeObras extends Obra {
	// Classe responsável para cadastrar as obras
	
	private static final String CAMINHO = "listaDeLivros.json";
	
	static ArrayList<String> codigoLivros = new ArrayList<>();
	
	public static void cadastrar(Obra novoLivro) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();	
		try (FileWriter escritor = new FileWriter(CAMINHO)) {
			
			gson.toJson(novoLivro, escritor);	
			System.out.println("Cadastro concluido!");
			
		} catch (IOException e) {
			System.out.println("Ocorreu um erro de leitura ou escrita!");
		}
	}
}
