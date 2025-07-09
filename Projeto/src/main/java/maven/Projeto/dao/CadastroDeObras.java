package maven.Projeto.dao;

//Bibliotecas

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.List;

// Imports

import maven.Projeto.model.Obra;

public abstract class CadastroDeObras extends Obra {
	// Classe responsável para cadastrar as obras
	
	private static final String CAMINHO = "listaDeLivros.json";
	
	static ArrayList<Obra> indexLivros = new ArrayList<>();
	
	public static void cadastrar(Obra novoLivro) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();	
		try (FileWriter listaDeLivros = new FileWriter(CAMINHO)) {
			gson.toJson(novoLivro, listaDeLivros);	
			System.out.println("Cadastro concluido!");
			indexLivros.add(novoLivro);
		} catch (IOException e) {
			System.out.println("Ocorreu um erro de leitura ou escrita!");
		}
	}
}
