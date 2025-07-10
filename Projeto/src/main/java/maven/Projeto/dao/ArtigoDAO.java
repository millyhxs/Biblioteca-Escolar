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
import maven.Projeto.model.Artigo; 


public class ArtigoDAO {
	
	private static final String CAMINHO = "listaDeArtigo.json";
	
    public static void cadastrar(Artigo novaObra) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Artigo> listaDeObras = new ArrayList<>();
        
        // lê o conteúdo existente do arquivo JSON (se existir)
        try (FileReader leitor = new FileReader(CAMINHO)) {
            Type tipoLista = new TypeToken<List<Artigo>>() {}.getType();
            listaDeObras = gson.fromJson(leitor, tipoLista);
            
            if (listaDeObras == null) {
                listaDeObras = new ArrayList<>();
            }
            
        } catch (IOException e) {
            // se o arquivo não existir ainda, criar um novo.
            listaDeObras = new ArrayList<>();
        }
       
        boolean codigoRepetido = false;
        for (Artigo artigo : listaDeObras) {
            if (artigo.getCodigo().equals(novaObra.getCodigo())) {
                codigoRepetido = true;
                break;
            }
        }
        
        if (codigoRepetido) {
            System.out.println("Erro: Já existe um artigo cadastrado com o código \"" + novaObra.getCodigo() + "\".");
            return;
        }
     
        listaDeObras.add(novaObra);
        
        // grava a lista atualizada de volta no JSON
        try (FileWriter escritor = new FileWriter(CAMINHO)) {
            gson.toJson(listaDeObras, escritor);
            System.out.println("Cadastro concluído!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo JSON!");
        }
    }
}
