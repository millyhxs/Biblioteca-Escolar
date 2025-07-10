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
import maven.Projeto.model.Revista;

public class RevistaDAO {
	
	private static final String CAMINHO = "listaDeRevistas.json";
	
    public static void cadastrar(Revista novaObra) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Revista> listaDeObras = new ArrayList<>();
        
        try (FileReader leitor = new FileReader(CAMINHO)) {
            Type tipoLista = new TypeToken<List<Revista>>() {}.getType();
            listaDeObras = gson.fromJson(leitor, tipoLista);
            
            if (listaDeObras == null) {
                listaDeObras = new ArrayList<>();
            }
            
        } catch (IOException e) {
            // se o arquivo não existir ainda, criaremos um novo.
            listaDeObras = new ArrayList<>();
        }
     
        boolean codigoRepetido = false;
        for (Revista revista : listaDeObras) {
            if (revista.getCodigo().equals(novaObra.getCodigo())) {
                codigoRepetido = true;
                break;
            }
        }
        
        if (codigoRepetido) {
            System.out.println("Erro: Já existe uma revista cadastrada com o código \"" + novaObra.getCodigo() + "\".");
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
