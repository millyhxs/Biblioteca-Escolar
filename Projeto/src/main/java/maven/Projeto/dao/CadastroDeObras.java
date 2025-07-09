package maven.Projeto.dao;

//Bibliotecas

import com.google.gson.Gson;

// Imports

import maven.Projeto.model.Obra;

public abstract class CadastroDeObras extends Obra {
	// Classe responsável para cadastrar as obras
	
	
	public void cadastrar(Gson teste) {
		Gson gson = new Gson();		
		String json = gson.toJson(teste);
		
	}
	
}
