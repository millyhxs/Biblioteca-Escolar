package maven.Projeto.dao;

//Bibliotecas

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

// Imports

import maven.Projeto.model.Livro;

public abstract class CadastroDeObras extends Livro {
	
	private static final String CAMINHO = "listaDeLivros.json";
	
    // Método para cadastrar uma nova obra
    public static void cadastrar(Livro novaObra) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Livro> listaDeObras = new ArrayList<>();
        
        // 1. Lê o conteúdo existente do arquivo JSON (se existir)
        try (FileReader leitor = new FileReader(CAMINHO)) {
            Type tipoLista = new TypeToken<List<Livro>>() {}.getType();
            listaDeObras = gson.fromJson(leitor, tipoLista);
            
            if (listaDeObras == null) {
                listaDeObras = new ArrayList<>();
            }
            
        } catch (IOException e) {
            // Se o arquivo não existir ainda, criaremos um novo.
            listaDeObras = new ArrayList<>();
        }
        // 2. Verifica se já existe um livro com o mesmo código
        boolean codigoRepetido = false;
        for (Livro livro : listaDeObras) {
            if (livro.getCodigo().equals(novaObra.getCodigo())) {
                codigoRepetido = true;
                break;
            }
        }
        
        if (codigoRepetido) {
            System.out.println("Erro: Já existe um livro cadastrado com o código \"" + novaObra.getCodigo() + "\".");
            return;
        }
        // 3. Adiciona a nova obra à lista
        listaDeObras.add(novaObra);
        
        // 4. Grava a lista atualizada de volta no JSON
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            gson.toJson(listaDeObras, escritor);
            System.out.println("Cadastro concluído!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo JSON!");
        }
    }
}
